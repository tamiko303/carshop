package com.artocons.carshop.util;

import com.artocons.carshop.persistence.model.Car;

import java.math.BigDecimal;
import java.util.HashSet;

public class ProductTestHelper {

    public static Car createTestCar(Long carId, String brand, String model, BigDecimal expectedPrice) {
        return new Car(
                carId,
                brand,
                model,
                "Description of the car",
                expectedPrice,
                2020L,
                15000L,
                "Sedan",
                "Petrol",
                "2.0L",
                "Automatic",
                new HashSet<>()
        );
    }
}
