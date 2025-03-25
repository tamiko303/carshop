package com.artocons.carshop.persistence.model;

import com.artocons.carshop.persistence.dtos.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product")
    @NotNull
    private Long product;

//    @Column(name = "user")
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

    public <Cart> Cart(Long product, int quantity, String description) {
//        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.date = new Date();
        this.description = description;
    }

    public static CartDTO convertToCartDTO(Cart cart) {
        return new CartDTO( cart.product,
                            cart.quantity,
                            cart.description );
    }
}
