package com.artocons.carshop.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "stock")
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "stock")
    @NotNull
    private Long stock;

    @Column(name = "reserved")
    @NotNull
    private Long reserved;

}
