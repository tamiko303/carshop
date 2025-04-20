package com.artocons.carshop.persistence.model;

import com.artocons.carshop.persistence.dtos.OrderItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequest {
    private String firstName;
    private String lastName;
    private String adress;
    private String phone;
    private String description;

    private List<OrderItem> data = new ArrayList<>();
}
