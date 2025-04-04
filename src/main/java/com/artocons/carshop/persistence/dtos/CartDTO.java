package com.artocons.carshop.persistence.dtos;

import com.artocons.carshop.persistence.model.Cart;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long product;
    private Integer quantity;
    private Date date;
    private String description;

    public CartDTO(Long product, Integer quantity, String description) {}

//    public static Cart convertToCartEntity(CartDTO cartDTO) {
//        return new Cart( cartDTO.product,
//                         cartDTO.quantity,
//                         cartDTO.description );
//    }
}
