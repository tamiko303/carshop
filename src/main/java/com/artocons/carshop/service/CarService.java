package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.repository.CarRepository;
import com.artocons.carshop.util.CarShopHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarService{

    @Value("5")
    private Integer carsPerPage;

    private final CarRepository carRepository;
    private final StockService stockService;

    public Page<Car> getCarsPage(Pageable pageable ) {
        return carRepository.findAll(pageable);
    }

    public Page<Car> getAvailableCarsList(String query, int pageNo, String sortField, String sortDirection, Pageable pageable) {

        List<Car> cars = searchCars(query);
        List<Stock> stocks = stockService.getAllAvailableCarsId();

        List<Car> availableCars = CarShopHelper.findIntersection(cars, stocks);

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageRequest = PageRequest.of(pageNo - 1, carsPerPage, sort);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), availableCars.size());

        List<Car> pageContent = availableCars.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, availableCars.size());
    }

    public List<Car> searchCars(String query) {
        return carRepository.searchByBrandOrModel(query, Pageable.unpaged()).getContent();
    }

    public BigDecimal getPriceById(long productId) throws ResourceNotFoundException {
        Car carItem = carRepository.findById(productId).orElse(null);

        AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);
        if (carItem != null) { price.set(carItem.getPrice()); }

        return price.get();
    }

    public Car getCarByIdOrNull(long productId) {
        return carRepository.findById(productId).orElse(null);
    }
}