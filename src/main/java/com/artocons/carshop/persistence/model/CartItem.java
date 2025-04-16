package com.artocons.carshop.persistence.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "product")
//    @NotNull
//    private Long product;

    @OneToOne
    @JoinColumn(name = "product")
    private Car car;

//    @Column(name = "user")
//    @NotNull
//    private Long user;

    @Column(name = "quantity")
    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Column(name = "date")
    @NotNull
    private Date date;

    @Column(name = "description")
    @Lob
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
