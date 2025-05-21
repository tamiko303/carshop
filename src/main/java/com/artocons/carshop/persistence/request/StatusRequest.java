package com.artocons.carshop.persistence.request;

import com.artocons.carshop.persistence.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StatusRequest {
    private OrderStatus status;
}
