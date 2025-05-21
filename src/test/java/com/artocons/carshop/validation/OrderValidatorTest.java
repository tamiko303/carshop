package com.artocons.carshop.validation;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.model.ValidOrderItems;
import com.artocons.carshop.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderValidatorTest {

//    @Mock
//    private StockService stockService;
//
//    private OrderValidator orderValidator;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        orderValidator = new OrderValidator(stockService);
//    }
//
//    private Car createTestCar(Long id, String brand, String model, BigDecimal expectedPrice) {
//        return new Car(
//                id,
//                brand,
//                model,
//                "Description of the car",
//                expectedPrice,
//                2020L,
//                15000L,
//                "Sedan",
//                "Petrol",
//                "2.0L",
//                "Automatic",
//                new HashSet<>()
//        );
//    }
//
//    @Test
//    public void testValidate_AllItemsAvailable() throws ResourceNotFoundException, ResourceVaidationException {
//        List<OrderItem> items = new ArrayList<>();
//
//        Car product1 = createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000));
//        OrderItem orderItem1 = new OrderItem(product1, 5);
//        items.add(orderItem1);
//
//        Car product2 = createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));
//        OrderItem orderItem2 = new OrderItem(product2, 3);
//        items.add(orderItem2);
//
//        Stock stock1 = new Stock();
//        stock1.setProductId(1L);
//        stock1.setStock(10L);
//
//        Stock stock2 = new Stock();
//        stock1.setProductId(2L);
//        stock2.setStock(5L);
//
//        when(stockService.getStocksByCarId(product1.getId())).thenReturn(stock1);
//        when(stockService.getStocksByCarId(product2.getId())).thenReturn(stock2);
//
//        ValidOrderItems validOrderItems = orderValidator.validate(items);
//
//        assertEquals("", validOrderItems.getMessage());
//        assertEquals(2, validOrderItems.getValidItems().size());
//    }
//
//    @Test
//    public void testValidate_SomeItemsUnavailable() throws ResourceNotFoundException, ResourceVaidationException {
//        List<OrderItem> items = new ArrayList<>();
//
//        Car product1 = createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000));
//        OrderItem orderItem1 = new OrderItem(product1, 15);
//        items.add(orderItem1);
//
//        Car product2 = createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));
//        OrderItem orderItem2 = new OrderItem(product2, 3);
//        items.add(orderItem2);
//
//        Stock stock1 = new Stock();
//        stock1.setProductId(1L);
//        stock1.setStock(10L);
//
//        Stock stock2 = new Stock();
//        stock1.setProductId(2L);
//        stock2.setStock(5L);
//
//        when(stockService.getStocksByCarId(product1.getId())).thenReturn(stock1);
//        when(stockService.getStocksByCarId(product2.getId())).thenReturn(stock2);
//
//        ValidOrderItems validOrderItems = orderValidator.validate(items);
//
//        assertEquals("1", validOrderItems.getMessage());
//        assertEquals(2, validOrderItems.getValidItems().size());
//
//        assertEquals(10, validOrderItems.getValidItems().get(0).getQuantity());
//        assertEquals(3, validOrderItems.getValidItems().get(1).getQuantity());
//    }
//
//    @Test
//    public void testValidate_SomeProductsNotFound() throws ResourceNotFoundException, ResourceVaidationException {
//        List<OrderItem> items = new ArrayList<>();
//
//        Car product1 = createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000));
//        OrderItem orderItem1 = new OrderItem(product1, 5);
//        items.add(orderItem1);
//
//        Car product2 =createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));
//        OrderItem orderItem2 = new OrderItem(product2, 3);
//        items.add(orderItem2);
//
//        when(stockService.getStocksByCarId(product1.getId())).thenThrow(new ResourceNotFoundException("Product not found"));
//        when(stockService.getStocksByCarId(product2.getId())).thenReturn(new Stock());
//
//        ValidOrderItems validOrderItems = new ValidOrderItems();
//        try {
//            validOrderItems = orderValidator.validate(items);
//        } catch (NullPointerException e) {
//            validOrderItems.setMessage("Product not found");
//        }
//
//        assertEquals("Product not found", validOrderItems.getMessage());
//        assertEquals(0, validOrderItems.getValidItems().size());
//    }
}
