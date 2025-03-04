package com.artocons.carshop.util;

import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.model.Car;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CarShopHelper {

    public static List<Car> findIntersection(List<Car> cars, List<Stock> stocks) {

        Set<Long> stockIds = new HashSet<>();

        for (Stock stock : stocks) {
            stockIds.add(stock.getProductId());
        }

        List<Car> intersection = new ArrayList<>();
        for (Car car : cars) {
            if (stockIds.contains(car.getId())) {
                intersection.add(car);
            }
        }

        return intersection;
    }
//        return   cars.stream()
//                     .filter(c -> c.getId().equals(stocks.get(0).getProductId()))
//                     .distinct()
//                     .collect(Collectors.toList());
}
