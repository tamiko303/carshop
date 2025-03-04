package com.artocons.carshop.persistence.model;

import javax.persistence.*;
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
    private Long product_id;

    @Column(name = "user")
    private Long user_id;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @Column(name = "date")
    @NotNull
    private Date date;

    @Column(name = "description")
    @Lob
//    @NotNull
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProduct_id() { return product_id; }
    public void setProduct_id(Long product_id) { this.product_id = product_id; }
    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
