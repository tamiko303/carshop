package com.artocons.carshop.persistence.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    private BigDecimal subTotal;
    private BigDecimal delivery;

    private String name;
    private String suname;
    private String adress;
    private String phone;
    private String description;

    private List<OrderItemDTO> items;
}
