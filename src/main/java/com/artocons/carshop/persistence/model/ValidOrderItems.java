package com.artocons.carshop.persistence.model;

import com.artocons.carshop.persistence.dtos.OrderItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ValidOrderItems {
    private String message;
    private List<OrderItemDTO> validItems = new ArrayList<>();
}
