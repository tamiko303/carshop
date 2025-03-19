package com.artocons.carshop.persistence.model;

import com.artocons.carshop.persistence.dtos.CartDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "product")
    @NotNull
    private Long product;

//    @Column(name = "user")
//    private Long user;

    @Setter
    @Getter
    @Column(name = "quantity")
    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Setter
    @Getter
    @Column(name = "date")
    @NotNull
    private Date date;

    @Setter
    @Getter
    @Column(name = "description")
    @Lob
    private String description;

    public Cart() {}

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
