package com.artocons.carshop.persistence.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultData {
    Integer count ;
    BigDecimal total;
}