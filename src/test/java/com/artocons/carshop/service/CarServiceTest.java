package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private StockService stockService;

    @InjectMocks
    private CarService carService;

    private final int carsPerPage = 5;

    private Car createTestCar(Long id, String brand, String model, BigDecimal expectedPrice) {
        return new Car(
                id,
                brand,
                model,
                "Description of the car",
                expectedPrice,
                2020L,
                15000L,
                "Sedan",
                "Petrol",
                "2.0L",
                "Automatic",
                new HashSet<>()
        );
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCarsPage() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<Car> expectedPage = new PageImpl<>(Arrays.asList(
                createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000)),
                createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000))
        ), pageable, 4); // totalElements = 4

        when(carRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Car> actualPage = carService.getCarsPage(pageable);

        assertThat(actualPage).isNotNull();
        assertThat(actualPage.getTotalElements()).isEqualTo(expectedPage.getTotalElements());
        assertThat(actualPage.getContent()).hasSize(expectedPage.getContent().size());

        assertThat(actualPage.getContent().get(0).getBrand()).isEqualTo(expectedPage.getContent().get(0).getBrand());
        assertThat(actualPage.getContent().get(1).getModel()).isEqualTo(expectedPage.getContent().get(1).getModel());
    }

    @Test
    public void testSearchCars() {
        String query = "Toyota";

        List<Car> expectedCars = Arrays.asList(
                createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000)),
                createTestCar(2L, "Toyota", "Corolla",  BigDecimal.valueOf(10000))
        );

        when(carRepository.searchByBrandOrModel(query, Pageable.unpaged())).thenReturn(new PageImpl<>(expectedCars));

        List<Car> actualCars = carService.searchCars(query);

        assertThat(actualCars).hasSize(expectedCars.size());
        assertThat(actualCars.get(0).getBrand()).isEqualTo(expectedCars.get(0).getBrand());
        assertThat(actualCars.get(1).getModel()).isEqualTo(expectedCars.get(1).getModel());
    }

    @Test
    public void testGetPriceById_Success() throws ResourceNotFoundException {
        long productId = 1L;
        BigDecimal expectedPrice = BigDecimal.valueOf(20000);

        Car testCar = createTestCar(productId, "Toyota", "Camry", expectedPrice);

        when(carRepository.findById(productId)).thenReturn(Optional.of(testCar));

        BigDecimal actualPrice = carService.getPriceById(productId);

        assertThat(actualPrice).isEqualTo(expectedPrice);
    }

    @Test
    public void testGetPriceById_ProductNotFound() {
        long productId = 1L;

        when(carRepository.findById(productId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> carService.getPriceById(productId))
                .withMessage("Product not found for id :: " + productId);
    }

    @Test
    public void testGetCarByIdOrNull_Success() {
        long productId = 1L;
        BigDecimal expectedPrice = BigDecimal.valueOf(20000);

        Car testCar = createTestCar(productId, "Toyota", "Camry", expectedPrice);

        when(carRepository.findById(productId)).thenReturn(Optional.of(testCar));

        Car actualCar = carService.getCarByIdOrNull(productId);

        assertThat(actualCar).isNotNull();
        assertThat(actualCar.getId()).isEqualTo(productId);
        assertThat(actualCar.getBrand()).isEqualTo("Toyota");
        assertThat(actualCar.getModel()).isEqualTo("Camry");
    }

    @Test
    public void testGetCarByIdOrNull_NotFound() {
        long productId = 1L;

        when(carRepository.findById(productId)).thenReturn(Optional.empty());

        Car actualCar = carService.getCarByIdOrNull(productId);

        assertThat(actualCar).isNull();
    }

//    @Test
//    public void testGetAvailableCarsList_Success() {
//        String query = "Toyota";
//        int pageNo = 1;
//        String sortField = "price";
//        String sortDirection = "ASC";
//        BigDecimal expectedPrice = BigDecimal.valueOf(20000);
//
//        List<Car> allCars = new ArrayList<>();
//        allCars.add(createTestCar(1L, "Toyota", "Camry", expectedPrice));
//        allCars.add(createTestCar(2L, "Honda", "Accord", expectedPrice));
//
//        List<Stock> availableStocks = new ArrayList<>();
//        availableStocks.add(new Stock(1L, 12L, 4L));
//
//        when(carRepository.findAll()).thenReturn(allCars);
//        when(stockService.getAllAvailableCarsId()).thenReturn(availableStocks);
//
//        Page<Car> resultPage = carService.getAvailableCarsList(query, pageNo, sortField, sortDirection, Pageable.unpaged());
//
//        assertThat(resultPage).isNotNull();
//        assertThat(resultPage.getTotalElements()).isEqualTo(1);
//        assertThat(resultPage.getContent()).hasSize(1);
//        assertThat(resultPage.getContent().get(0).getBrand()).isEqualTo("Toyota");
//    }
//
//    @Test
//    public void testGetAvailableCarsList_NoAvailableCars() {
//        String query = "Toyota";
//        int pageNo = 1;
//        String sortField = "price";
//        String sortDirection = "ASC";
//        BigDecimal expectedPrice = BigDecimal.valueOf(20000);
//
//        List<Car> allCars = new ArrayList<>();
//        allCars.add(createTestCar(1L, "Toyota", "Camry", expectedPrice));
//
//        List<Stock> availableStocks = new ArrayList<>();
//
//        when(carRepository.findAll()).thenReturn(allCars);
//        when(stockService.getAllAvailableCarsId()).thenReturn(availableStocks);
//
//        Page<Car> resultPage = carService.getAvailableCarsList(query, pageNo, sortField, sortDirection, Pageable.unpaged());
//
//        assertThat(resultPage).isNotNull();
//        assertThat(resultPage.getTotalElements()).isEqualTo(0);
//    }
//
//    @Test
//    public void testGetAvailableCarsList_EmptyQuery() {
//        String query = "";
//        int pageNo = 1;
//        String sortField = "price";
//        String sortDirection = "ASC";
//        BigDecimal expectedPrice = BigDecimal.valueOf(20000);
//
//        List<Car> allCars = new ArrayList<>();
//        allCars.add(createTestCar(1L, "Toyota", "Camry", expectedPrice));
//        allCars.add(createTestCar(2L, "Honda", "Accord", expectedPrice));
//
//        List<Stock> availableStocks = new ArrayList<>();
//        availableStocks.add(new Stock(1L, 12L, 4L));
//        availableStocks.add(new Stock(2L, 23L, 7L));
//
//        when(carRepository.findAll()).thenReturn(allCars);
//        when(stockService.getAllAvailableCarsId()).thenReturn(availableStocks);
//
//        Page<Car> resultPage = carService.getAvailableCarsList(query, pageNo, sortField, sortDirection, Pageable.unpaged());
//
//        assertThat(resultPage).isNotNull();
//        assertThat(resultPage.getTotalElements()).isEqualTo(2);
//    }

}
