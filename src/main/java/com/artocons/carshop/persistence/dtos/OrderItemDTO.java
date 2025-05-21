package com.artocons.carshop.persistence.dtos;

import com.artocons.carshop.persistence.model.Color;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private String brand;
    private String model;
    private long productionYear;
    private BigDecimal price;
    private int quantity;
    private Set<Color> colors = new HashSet<>();

}
