package com.artocons.carshop.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ValidOrderItems {
    private String message;
    private List<OrderItem> validItems = new ArrayList<>();
}
