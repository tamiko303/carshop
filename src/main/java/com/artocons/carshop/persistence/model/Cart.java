package com.artocons.carshop.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "cart")
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
    private Integer quantity;

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

    public Cart() {

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProduct() { return product; }
    public void setProduct(Long product) { this.product = product; }
//    public Long getUser() { return user; }
//    public void setUser(Long user) { this.user = user; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
