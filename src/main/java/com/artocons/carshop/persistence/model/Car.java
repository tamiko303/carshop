package com.artocons.carshop.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.eclipse.jdt.internal.compiler.codegen.ConstantPool.ToString;

@Entity
@Table(name = "car")
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

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Long productionYear) {
        this.productionYear = productionYear;
    }

    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getGearboxType() {
        return gearboxType;
    }

    public void setGearboxType(String gearboxType) {
        this.gearboxType = gearboxType;
    }

    public Set<Color> getColors() { return colors; }

    public void setColors(Set<Color> colors) { this.colors = colors; }

    public String convertColorsToString(Set<Color> colors) {
        return colors.stream()
                     .map(color -> color.getColorName())
                     .collect(Collectors.joining(" "));
    }
}
