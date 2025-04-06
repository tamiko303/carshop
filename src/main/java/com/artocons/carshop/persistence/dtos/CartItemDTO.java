package com.artocons.carshop.persistence.dtos;

import com.artocons.carshop.persistence.model.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long product;
    private String brand;
    private String model;
    private Set<Color> colors;
    private Long productionYear;
    private BigDecimal price;
    private int quantity;
    private Date date;
    private String description;

    public CartItemDTO(Long id, Long product, Integer quantity, String description) {}

}
