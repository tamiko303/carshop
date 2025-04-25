package com.artocons.carshop.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "car")
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    @NotNull
    private String brand;

    @Column(name = "model")
    @NotNull
    private String model;

    @Column(name = "description")
    @Lob
    @NotNull
    private String description;

    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Column(name = "production_year")
    @NotNull
    private Long productionYear;

    @Column(name = "mileage")
    @NotNull
    private Long mileage;

    @Column(name = "body_type")
    @NotNull
    private String bodyType;

    @Column(name = "engine_type")
    @NotNull
    private String engineType;

    @Column(name = "engine_capacity")
    @NotNull
    private String engineCapacity;

    @Column(name = "gearbox_type")
    @NotNull
    private String gearboxType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "car_colors",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "color_id", referencedColumnName = "id")
    )
    private Set<Color> colors = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    public String convertColorsToString(Set<Color> colors) {
        return colors.stream()
                     .map(Color::getName)
                     .collect(Collectors.joining(" "));
    }
}
