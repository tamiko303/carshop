package com.artocons.carshop.persistence.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderHeader order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Car product;

    public OrderItem(Car product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}
