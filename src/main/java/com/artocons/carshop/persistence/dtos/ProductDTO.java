package com.artocons.carshop.persistence.dtos;

import com.artocons.carshop.persistence.model.Car;

import java.math.BigDecimal;

public class ProductDTO {
    private String brand;
    private String model;
    private String description;
    private BigDecimal price;
    private Long productionYear;
    private Long mileage;
    private String bodyType;
    private String engineType;
    private String engineCapacity;
    private String gearboxType;

    public ProductDTO() {}
    public ProductDTO(String model,
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

    public static Car convertToCarEntity(ProductDTO productDTO) {
        return new Car( productDTO.model,
                        productDTO.brand,
                        productDTO.description,
                        productDTO.price,
                        productDTO.productionYear,
                        productDTO.mileage,
                        productDTO.bodyType,
                        productDTO.engineType,
                        productDTO.engineCapacity,
                        productDTO.gearboxType);
    }
}
