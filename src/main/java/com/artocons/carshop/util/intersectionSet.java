package com.artocons.carshop.util;

import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.model.Car;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class intersectionSet(List<Car> a, List<Stock> b ) {
        return Stream.of(a)
                .filter(new LinkedList<>(Arrays.asList(b))::remove)
                .toArray(Integer[]::new);
    }
}
