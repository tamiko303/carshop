package com.artocons.carshop.persistence.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "order_id")
    private Long order;

    @Column(name = "product_id")
    private Long product;

    public OrderItem(Long product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.getProduct() + " " + this.getQuantity();
    }
}
