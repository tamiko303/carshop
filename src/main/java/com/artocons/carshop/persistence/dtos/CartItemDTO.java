package com.artocons.carshop.persistence.dtos;

import com.artocons.carshop.persistence.model.Color;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
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
