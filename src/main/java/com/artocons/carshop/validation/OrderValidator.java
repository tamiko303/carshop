package com.artocons.carshop.validation;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.model.ValidOrderItems;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final StockService stockService;

    public ValidOrderItems validate(List<OrderItem> items) throws ResourceNotFoundException, ResourceVaidationException {

        ValidOrderItems validOrderItems = new ValidOrderItems();
        List<Long> errorIds = new ArrayList<>();
        List<OrderItem> newItems = new ArrayList<>();

        items.forEach(orderItem -> {
            try {
                Stock stock = stockService.getStocksByCarId(orderItem.getProduct().getId());

                int orderItemQuantity = orderItem.getQuantity();

                if (orderItemQuantity > stock.getStock()) {
                    errorIds.add(orderItem.getProduct().getId());
                    orderItem.setQuantity((int) (stock.getStock() - orderItemQuantity));
                    newItems.add(orderItem);

                } else {
                    newItems.add(orderItem);
                }

            } catch (ResourceNotFoundException e) {
                errorIds.add(orderItem.getProduct().getId());
            }
        });

        boolean isNotEmpty = errorIds.stream().findAny().isPresent();

        if (isNotEmpty) {
            String message = errorIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            validOrderItems.setMessage(message);
            validOrderItems.setValidItems(newItems);
        } else {
            validOrderItems.setMessage("");
            validOrderItems.setValidItems(newItems);
        }
    return validOrderItems;

    }
}
