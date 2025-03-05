package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Resource
    private CarRepository carRepository;

    public Page<Car> getCarsPage(Pageable pageable ) {
        return carRepository.findAll(pageable);
    }

    public List<Car>  getCarsFilteredByQuery(String query) {

        if (query.isEmpty()) {
            return carRepository.findAll(Pageable.unpaged()).getContent();
        } else {
            return carRepository.findAll(Pageable.unpaged()).getContent()
                    .stream()
                    .filter(i -> i.getBrand().toLowerCase().contains(query.toLowerCase())
                            || i.getModel().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

    public Page<Car> searchCars(String query, Pageable pageable) {
        return carRepository.searchByBrandOrModel(query, pageable);
    }

    public BigDecimal getPriceById(long productId) {
        Optional<Car> carItem = carRepository.findById(productId);

        AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.valueOf(0));
        carItem.ifPresent(item -> {
            price.set(item.getPrice());
        });

        return price.get();
    }

}