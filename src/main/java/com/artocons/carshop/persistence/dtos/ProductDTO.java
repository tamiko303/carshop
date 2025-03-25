package com.artocons.carshop.persistence.dtos;

//import com.artocons.carshop.persistence.model.Car;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
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

//    public static Car convertToCarEntity(ProductDTO productDTO) {
//        return new Car( productDTO.model,
//                        productDTO.brand,
//                        productDTO.description,
//                        productDTO.price,
//                        productDTO.productionYear,
//                        productDTO.mileage,
//                        productDTO.bodyType,
//                        productDTO.engineType,
//                        productDTO.engineCapacity,
//                        productDTO.gearboxType);
//    }
}
