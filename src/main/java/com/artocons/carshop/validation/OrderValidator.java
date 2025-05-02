package com.artocons.carshop.validation;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.model.ValidOrderItems;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.StockService;
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

    public ValidOrderItems validate(List<OrderItem> items) {
        ValidOrderItems validOrderItems = new ValidOrderItems();
        List<Long> errorIds = new ArrayList<>();
        List<OrderItem> newItems = new ArrayList<>();

        for (OrderItem orderItem : items) {
            try {
                Stock stock = stockService.getStocksByCarId(orderItem.getProduct().getId());
                Long availableStock = stock.getStock();
                int orderQuantity = orderItem.getQuantity();

                if (orderQuantity > availableStock) {
                    errorIds.add(orderItem.getProduct().getId());
                    int adjustedQuantity = (int) Math.max(availableStock, 0);
                    orderItem.setQuantity(adjustedQuantity);
                }
                newItems.add(orderItem);
            } catch (ResourceNotFoundException e) {
                errorIds.add(orderItem.getProduct().getId());
            }
        }

        validOrderItems.setValidItems(newItems);

        if (!errorIds.isEmpty()) {
            String errorIdsString = errorIds.stream().map(Object::toString).collect(Collectors.joining(","));
            session.setAttribute("orderErrorIds", errorIdsString);
        }

        return validOrderItems;
    }
}
