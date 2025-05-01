package com.artocons.carshop.persistence.response;

import com.artocons.carshop.persistence.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponse {
    String msg;
    String code;
    OrderStatus data;
}
