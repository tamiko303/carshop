package com.artocons.carshop.persistence.model;

import com.artocons.carshop.persistence.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@Table(name = "order_header")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false, nullable = false)
    private Long orderId;

    private LocalDate orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal subTotal;
    private BigDecimal delivery;
    private BigDecimal total;

    private String firstName;
    private String lastName;
    private String adress;
    private String phone;
    private String description;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
}
