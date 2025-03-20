package com.artocons.carshop.validation;

import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.springframework.data.util.CastUtils.cast;

@Component
@RequiredArgsConstructor
public class QuantityValidator  {
    private final StockService stockService;

//    public QuantityValidator(StockService stockService) {
//        this.stockService = stockService;

    public Errors validate(Cart cart) {

        Optional<Stock> stock = stockService.getStocksByCarId(cart.getProduct());

        Errors errors = null;
        if (cart.getQuantity() > (stock.orElseGet(() -> cast(0))).getStock())
            errors.rejectValue("quantity", "", "The required quantity is not available!");

        return errors;
    }

}
