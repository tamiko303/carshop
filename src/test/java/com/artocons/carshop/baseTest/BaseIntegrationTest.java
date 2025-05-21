package com.artocons.carshop.baseTest;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.repository.CarRepository;
import com.artocons.carshop.persistence.repository.OrderRepository;
import com.artocons.carshop.persistence.repository.StockRepository;
import com.artocons.carshop.util.OrderTestHelper;
import com.artocons.carshop.util.ProductTestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Transactional
public abstract class BaseIntegrationTest {

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    protected HttpSession session;

    public void setUp() throws ResourceNotFoundException, ResourceVaidationException {

        orderRepository.deleteAll();
        carRepository.deleteAll();
        stockRepository.deleteAll();
        session.invalidate();

        Set<OrderItem> orderItems = new HashSet<>();

        Car product1 = ProductTestHelper.createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000));
        carRepository.save(product1);

        OrderItem item1 = new OrderItem(product1.getId(), 2);

        Car product2 = ProductTestHelper.createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));
        carRepository.save(product2);

        OrderItem item2 = new OrderItem(product2.getId(), 3);

        orderItems.add(item1);
        orderItems.add(item2);

        OrderHeader savedOrder = OrderTestHelper.createTestOrder(1L, orderItems);

        orderRepository.save(savedOrder);

        session.setAttribute("order", savedOrder);

        Stock stock1 = new Stock();
        stock1.setProductId(1L);
        stock1.setStock(10L);
        stock1.setReserved(2L);
        stockRepository.save(stock1);

        Stock stock2 = new Stock();
        stock2.setProductId(2L);
        stock2.setStock(2L);
        stock2.setReserved(2L);
        stockRepository.save(stock2);

    }

}

