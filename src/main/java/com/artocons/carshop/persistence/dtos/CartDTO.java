package com.artocons.carshop.persistence.dtos;

import com.artocons.carshop.persistence.model.Cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CartDTO {

    private Long product;
    private Integer quantity;
    private Date date;
    private String description;

    public CartDTO(Long product, Integer quantity, String description) {}
    public CartDTO(Long product, Integer quantity, Date date, String description) {
        this.product = product;
        this.quantity = quantity;
        this.date = date;
        this.description = description;
    }

    public static Cart convertToCartEntity(CartDTO cartDTO) {
        return new Cart( cartDTO.product,
                         cartDTO.quantity,
                         cartDTO.description );
    }
}
