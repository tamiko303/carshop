package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CarService{

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

    public List<Car> searchCars(String query) {
        return carRepository.searchByBrandOrModel(query, Pageable.unpaged()).getContent();
    }

    public BigDecimal getPriceById(long productId) throws ResourceNotFoundException {
        Car carItem = carRepository.findById(productId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Product not found for id :: " + productId));

        AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.valueOf(0));
        price.set(carItem.getPrice());

        return price.get();
    }

    public Car getCarById(long productId) throws ResourceNotFoundException {
        return carRepository.findById(productId)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found for id :: " + productId));
    }

}