package com.artocons.carshop.persistence.dtos;

import com.artocons.carshop.persistence.model.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderHeaderDTO {

    private Long orderId;
    private BigDecimal subTotal;
    private BigDecimal delivery;
    private BigDecimal total;

    private String firstName;
    private String lastName;
    private String adress;
    private String phone;
    private String description;

    private String message;

    private Set<OrderItem> items = new HashSet<>();
}
