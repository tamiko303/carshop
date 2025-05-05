package com.artocons.carshop.persistence.model;

import com.artocons.carshop.persistence.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "car_rating")
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @Column(name = "product")
    @NotNull
    private Long productId;

    @Column(name = "rating_date")
    @NotNull
    private LocalDate ratingDate;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(name = "comment")
    private String comment;
}
