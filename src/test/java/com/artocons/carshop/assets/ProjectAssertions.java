package com.artocons.carshop.assets;

import com.artocons.carshop.persistence.model.Car;

public class ProjectAssertions {
    public static CarAssert assertCar(Car actual) {
        return  new CarAssert(actual);
    }
}
