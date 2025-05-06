package com.artocons.carshop.validation;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.dtos.OrderItemDTO;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.model.ValidOrderItems;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.StockService;
import com.artocons.carshop.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final StockService stockService;
    private final HttpSession session;
    private final CarService carService;

    public ValidOrderItems validate(List<OrderItem> items) {
        ValidOrderItems validOrderItems = new ValidOrderItems();
        List<Long> errorIds = new ArrayList<>();
        List<OrderItem> newItems = new ArrayList<>();

        for (OrderItem orderItem : items) {
            try {
                Stock stock = stockService.getStocksByCarId(orderItem.getProduct());
                Long availableStock = stock.getStock();
                int orderQuantity = orderItem.getQuantity();

                if (orderQuantity > availableStock) {
                    errorIds.add(orderItem.getProduct());
                    int adjustedQuantity = (int) Math.max(availableStock, 0);
                    orderItem.setQuantity(adjustedQuantity);
                }
                newItems.add(orderItem);
            } catch (ResourceNotFoundException e) {
                errorIds.add(orderItem.getProduct());
            }
        }

        List<OrderItemDTO> newItemsDTO = new ArrayList<>();
        for (OrderItem orderItem : newItems) {
            Car product = carService.getCarByIdOrNull(orderItem.getProduct());
            newItemsDTO.add(MappingUtils.convertToOrderItemDTO(orderItem, product));
        }
        validOrderItems.setValidItems(newItemsDTO);

        if (!errorIds.isEmpty()) {
            String errorIdsString = errorIds.stream().map(Object::toString).collect(Collectors.joining(","));
            session.setAttribute("orderErrorIds", errorIdsString);
        }

        return validOrderItems;
    }
}
