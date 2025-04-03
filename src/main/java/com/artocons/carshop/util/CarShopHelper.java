package com.artocons.carshop.util;

import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.model.Car;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CarShopHelper {

    public static List<Car> findIntersection(List<Car> cars, List<Stock> stocks) {

        Set<Long> stockIds = stocks.stream()
                .map(Stock::getProductId)
                .collect(Collectors.toSet());

        return cars.stream()
                .filter(c ->stockIds.contains(c.getId()))
                .collect(Collectors.toList());
    }
}
