package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.repository.CarRepository;
import com.artocons.carshop.persistence.repository.StockRepository;
import com.artocons.carshop.util.ProductTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class CarServiceIntegrationTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {

        carRepository.deleteAll();
        stockRepository.deleteAll();

        Car product1 = ProductTestHelper.createTestCar(1L, "Honda", "Accord",  BigDecimal.valueOf(20000));
        Car product2 = ProductTestHelper.createTestCar(2L, "Toyota", "Camry",  BigDecimal.valueOf(10000));
        carRepository.save(product1);
        carRepository.save(product2);

        Stock stock1 = new Stock();
        stock1.setProductId(product1.getId());
        stock1.setStock(10L);
        stock1.setReserved(2L);

        Stock stock2 = new Stock();
        stock2.setProductId(product2.getId());
        stock2.setStock(2L);
        stock2.setReserved(2L);

        stockRepository.save(stock1);
        stockRepository.save(stock2);
    }

    @Test
    public void testGetAvailableCarsList() {
        String query = "Honda";
        int pageNo = 1;
        String sortField = "price";
        String sortDirection = "ASC";

        List<Car> foundProducts = carRepository.findAll();

        Page<Car> result = carService.getAvailableCarsList(query, pageNo, sortField, sortDirection, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Honda", result.getContent().get(0).getBrand());
    }

    @Test
    public void testGetCarsPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Car> result = carService.getCarsPage(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetPriceById_WhenCarDoesNotExist_ShouldThrowResourceNotFoundException() {
        long productId = 999L;

        List<Car> foundProducts = carRepository.findAll();

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> carService.getPriceById(productId));

        assertTrue(exception.getMessage().contains("Product not found for id :: " + productId));
    }

    @Test
    public void testGetCarByIdOrNull_WhenCarExists_ShouldReturnCar() {
        long productId = 26L;

        List<Car> foundProducts = carRepository.findAll();

        Car result = carService.getCarByIdOrNull(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    public void testGetCarByIdOrNull_WhenCarDoesNotExist_ShouldReturnNull() {
        long productId = 999L;

        List<Car> foundProducts = carRepository.findAll();

        Car result = carService.getCarByIdOrNull(productId);

        assertNull(result);
    }

    @Test
    public void testGetPriceById_WhenCarExists_ShouldReturnPrice() throws ResourceNotFoundException {
        long productId = 28L;

        List<Car> foundProducts = carRepository.findAll();

        BigDecimal price = carService.getPriceById(productId);

        assertEquals(BigDecimal.valueOf(20000), price);
    }
}
