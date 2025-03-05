package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CarService {

    @Resource
    private CarRepository carRepository;

    public Page<Car> getCarsPage(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public Page<Car> searchCars(String query, Pageable pageable) {
        return carRepository.searchByBrandOrModel(query, pageable);
    }


}
