package com.artocons.carshop.validation;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuantityValidator  {
    private final StockService stockService;

    public void validate(Cart cart) throws ResourceNotFoundException {

        Stock stock = stockService.getStocksByCarId(cart.getProduct());

        if (cart.getQuantity() > stock.getStock())
            throw new IllegalArgumentException("The required quantity is not available!");
    }

}
