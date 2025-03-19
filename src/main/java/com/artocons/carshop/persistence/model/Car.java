package com.artocons.carshop.persistence.model;

import com.artocons.carshop.persistence.dtos.ProductDTO;
import lombok.Getter;
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
public class Car {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "brand")
    @NotNull
    private String brand;

    @Setter
    @Getter
    @Column(name = "model")
    @NotNull
    private String model;

    @Setter
    @Getter
    @Column(name = "description")
    @Lob
    @NotNull
    private String description;

    @Setter
    @Getter
    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Setter
    @Getter
    @Column(name = "production_year")
    @NotNull
    private Long productionYear;

    @Setter
    @Getter
    @Column(name = "mileage")
    @NotNull
    private Long mileage;

    @Setter
    @Getter
    @Column(name = "body_type")
    @NotNull
    private String bodyType;

    @Setter
    @Getter
    @Column(name = "engine_type")
    @NotNull
    private String engineType;

    @Setter
    @Getter
    @Column(name = "engine_capacity")
    @NotNull
    private String engineCapacity;

    @Setter
    @Getter
    @Column(name = "gearbox_type")
    @NotNull
    private String gearboxType;

    @Setter
    @Getter
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "car_colors",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "color_id", referencedColumnName = "id")
    )
    private Set<Color> colors = new HashSet<>();

    public Car(){}
    public Car(String model,
               String brand,
               String description,
               BigDecimal price,
               Long productionYear,
               Long mileage,
               String bodyType,
               String engineType,
               String engineCapacity,
               String gearboxType) {
        this.model = model;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.bodyType = bodyType;
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
        this.gearboxType = gearboxType;
    }
    public Car(Long id,
               String model,
               String brand,
               String description,
               BigDecimal price,
               Long productionYear,
               Long mileage,
               String bodyType,
               String engineType,
               String engineCapacity,
               String gearboxType,
               Set<Color> colors) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.bodyType = bodyType;
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
        this.gearboxType = gearboxType;
        this.colors = colors;
    }

    public static ProductDTO convertToProductDTO(Car car) {
        return new ProductDTO(  car.model,
                                car.brand,
                                car.description,
                                car.price,
                                car.productionYear,
                                car.mileage,
                                car.bodyType,
                                car.engineType,
                                car.engineCapacity,
                                car.gearboxType     );
    }

    public String convertColorsToString(Set<Color> colors) {
        return colors.stream()
                     .map(color -> color.getColorName())
                     .collect(Collectors.joining(" "));
    }
}
