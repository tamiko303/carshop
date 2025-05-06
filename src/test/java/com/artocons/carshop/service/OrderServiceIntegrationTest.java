package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.dtos.OrderItemDTO;
import com.artocons.carshop.persistence.enums.OrderStatus;
import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.persistence.repository.CarRepository;
import com.artocons.carshop.persistence.repository.OrderRepository;
import com.artocons.carshop.persistence.repository.StockRepository;
import com.artocons.carshop.persistence.request.OrderRequest;
import com.artocons.carshop.util.OrderTestHelper;
import com.artocons.carshop.util.ProductTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    protected HttpSession session;

    @BeforeEach
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
        stock1.setProductId(product1.getId());
        stock1.setStock(10L);
        stock1.setReserved(2L);

        Stock stock2 = new Stock();
        stock2.setProductId(product2.getId());
        stock2.setStock(2L);
        stock2.setReserved(2L);

        stockRepository.save(stock1);
        stockRepository.save(stock2);

        Cart cartItem = new Cart();
        cartItem.setProduct(product1.getId());
        cartItem.setQuantity(2);
        ResultData data = cartService.addItemToCart(cartItem);

        session.setAttribute("cart", cartItem);
    }

    @Test
    void testShowOrderPage() {
        PageImpl<OrderItemDTO> orderPage = orderService.showOrderPage(PageRequest.of(0, 10));

        assertThat(orderPage.getTotalElements()).isGreaterThan(0);
    }

    @Test
    void testGetAllOrders() {
        Page<OrderHeader> orders = orderService.getAllOrders(PageRequest.of(0, 10));

        assertThat(orders.getTotalElements()).isEqualTo(1);
    }

    @Test
    void testPlaceOrder() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setFirstName("John");
        orderRequest.setLastName("Doe");
        orderRequest.setAdress("123 Test St");
        orderRequest.setPhone("1233214");

        OrderHeader savedOrder = assertDoesNotThrow(() -> orderService.placeOrder(orderRequest));

        assertNotNull(savedOrder.getOrderId());

        List<OrderHeader> orders = orderRepository.findAll();

        assertThat(orders).hasSize(2);
    }

    @Test
    void testSetStatus() {
        OrderHeader order = orderRepository.getOne(1L);

        OrderStatus updatedStatus = assertDoesNotThrow(() -> orderService.setStatus(order.getOrderId(), OrderStatus.DELIVERED));

        assertEquals(OrderStatus.DELIVERED, updatedStatus);

        OrderHeader updatedOrder = orderRepository.findById(order.getOrderId()).orElse(null);

        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.DELIVERED, updatedOrder.getOrderStatus());
    }

    @Test
    void testSetStatusNotFound() {
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> orderService.setStatus(999L, OrderStatus.DELIVERED));

        String expectedMessage = "Order not found for id :: 999";

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testGetOrderByIdOrNull_ExistingId() {

        OrderHeader foundOrder = orderService.getOrderByIdOrNull(1L);

        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getOrderItems()).isNotNull();
    }

    @Test
    void testGetOrderByIdOrNull_NonExistingId() {
        OrderHeader foundOrder = orderService.getOrderByIdOrNull(999L);

        assertThat(foundOrder).isNull();
    }
}
