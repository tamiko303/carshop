package com.artocons.carshop.validation;

import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.StockService;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import org.springframework.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.springframework.data.util.CastUtils.cast;

public class QuantityValidator implements Validator {

    @Resource
    private StockService stockService;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return Cart.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        Cart cart = (Cart) target;
        Optional<Stock> stock = stockService.getStocksByCarId(cart.getId());

        if (cart.getQuantity() > (stock.orElseGet(()->cast(0))).getStock()) {
            errors.rejectValue("quantity", "", "The required quantity is not available!");
        }

    }

}
