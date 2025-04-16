package com.artocons.carshop.persistence.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private int quantity;

    public OrderItemDTO(Long product, int quantity) {
        this.productId = product;
        this.quantity = quantity;
    }
}
