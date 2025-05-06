package com.artocons.carshop.assets;

import com.artocons.carshop.persistence.model.Car;
import org.assertj.core.api.AbstractAssert;

import java.math.BigDecimal;

public class CarAssert extends AbstractAssert<CarAssert, Car> {
    protected CarAssert(Car actual) {
        super(actual, CarAssert.class);
    }

    public static CarAssert assertThat(Car actual) {
        return new CarAssert(actual);
    }

    public CarAssert hasID(int id) {
        isNotNull();
        if (actual.getId() == null) {
            failWithMessage("Expected car ID to be <null> but was <%s>", id);
        }
        return this;
    }

    public CarAssert hasBrand(String brand) {
        isNotNull();
        if (!actual.getBrand().equals(brand)) {
            failWithMessage("Expected brand <%s> but was <%s>", brand, actual.getBrand());
        }
        return this;
    }

    public CarAssert hasModel(String model) {
        isNotNull();
        if (!actual.getModel().equals(model)) {
            failWithMessage("Expected model <%s> but was <%s>", model, actual.getModel());
        }
        return this;
    }

    public CarAssert hasDescription(String description) {
        isNotNull();
        if (!actual.getDescription().equals(description)) {
            failWithMessage("Expected description <%s> but was <%s>", description, actual.getDescription());
        }
        return this;
    }

    public CarAssert hasPrice(BigDecimal price) {
        isNotNull();
        if (!actual.getPrice().equals(price)) {
            failWithMessage("Expected price <%s> but was <%s>", price, actual.getPrice());
        }
        return this;
    }

    public CarAssert hasProductionYear(int production_year) {
        isNotNull();
        if (actual.getProductionYear() != production_year) {
            failWithMessage("Expected production_year <%s> but was <%s>", production_year, actual.getProductionYear());
        }
        return this;
    }

    public CarAssert hasMileage(int mileage) {
        isNotNull();
        if (actual.getMileage() != mileage) {
            failWithMessage("Expected mileager <%s> but was <%s>", mileage, actual.getMileage());
        }
        return this;
    }

    public CarAssert hasBodyType(String body_type) {
        isNotNull();
        if (!actual.getBodyType().equals(body_type)) {
            failWithMessage("Expected body_type <%s> but was <%s>", body_type, actual.getMileage());
        }
        return this;
    }

    public CarAssert hasEngineType(String engine_type) {
        isNotNull();
        if (!actual.getEngineType().equals(engine_type)) {
            failWithMessage("Expected engine_type <%s> but was <%s>", engine_type, actual.getEngineType());
        }
        return this;
    }

    public CarAssert hasEngineCopacity(String engine_copacity) {
        isNotNull();
        if (!actual.getEngineCapacity().equals(engine_copacity)) {
            failWithMessage("Expected engine_type <%s> but was <%s>", engine_copacity, actual.getEngineCapacity());
        }
        return this;
    }

    public CarAssert hasGearboxType(String gearbox_type) {
        isNotNull();
        if (!actual.getGearboxType().equals(gearbox_type)) {
            failWithMessage("Expected gearbox_type <%s> but was <%s>", gearbox_type, actual.getGearboxType());
        }
        return this;
    }
}
