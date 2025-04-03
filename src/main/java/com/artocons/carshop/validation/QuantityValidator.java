package com.artocons.carshop.validation;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Component
@RequiredArgsConstructor
public class QuantityValidator  {
    private final StockService stockService;
    private final InternalResourceViewResolver internalResourceViewResolver;

    public void validate(Cart cart) throws ResourceNotFoundException, ResourceVaidationException {

        Stock stock = stockService.getStocksByCarId(cart.getProduct());

        if (cart.getQuantity() > stock.getStock()) {
            throw new ResourceVaidationException("The required quantity is not available!");
        }
    }

}
