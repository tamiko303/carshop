package com.artocons.carshop.validation;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuantityValidator  {

    private final StockService stockService;

    public void validate(Cart cart) throws ResourceNotFoundException, ResourceVaidationException {

        Stock stock = stockService.getStocksByCarId(cart.getProduct());

        int newQuantity = cart.getQuantity();

        if (newQuantity > stock.getStock()) {
            throw new ResourceVaidationException("The required quantity is not available!");
        }
    }

}
