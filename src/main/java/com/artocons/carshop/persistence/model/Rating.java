package com.artocons.carshop.persistence.model;

import com.artocons.carshop.persistence.enums.RatingStar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "car_rating")
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @Column(name = "car_id")
    @NotNull
    private Long productId;

    @Column(name = "rating_date")
    @NotNull
    private LocalDate ratingDate;

//    @Enumerated(EnumType.ORDINAL)
    private int rating;

    @Column(name = "username")
    @NotNull
    private String userName;

    @Column(name = "comment")
    private String comment;

}
