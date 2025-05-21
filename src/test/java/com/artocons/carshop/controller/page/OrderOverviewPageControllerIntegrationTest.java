package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.enums.OrderStatus;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.repository.OrderRepository;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.OrderOverviewService;
import com.artocons.carshop.service.OrderService;
import com.artocons.carshop.util.OrderTestHelper;
import com.artocons.carshop.util.ProductTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc()
public class OrderOverviewPageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        Set<OrderItem> orderItems = new HashSet<>();

        Car product1 = ProductTestHelper.createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000));
        OrderItem item1 = new OrderItem(product1.getId(), 2);

        Car product2 = ProductTestHelper.createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));
        OrderItem item2 = new OrderItem(product2.getId(), 3);

        orderItems.add(item1);
        orderItems.add(item2);

        OrderHeader savedOrder = OrderTestHelper.createTestOrder(1L, orderItems);
        orderRepository.save(savedOrder);
    }
    @Test
    void getOrderById_ShouldReturnOverviewPage_WhenOrderExists() throws Exception {
        long orderId = 1L;

        mockMvc.perform(get("/order-overview/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("overviewPage"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("orderId"))
                .andExpect(model().attribute("orderId", orderId));
    }

    @Test
    void getOrderById_ShouldReturn404_WhenOrderDoesNotExist() throws Exception {
        long nonExistentOrderId = 999L;

        mockMvc.perform(get("/order-overview/" + nonExistentOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
