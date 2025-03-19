package com.artocons.carshop.persistence.model;

import lombok.Getter;
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
public class Stock {

    @Setter
    @Getter
    @Id
    @Column(name = "product_id")
    private Long productId;

    @Setter
    @Getter
    @Column(name = "stock")
    @NotNull
    private Long stock;

    @Setter
    @Getter
    @Column(name = "reserved")
    @NotNull
    private Long reserved;

}
