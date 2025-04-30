package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.enums.OrderStatus;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.OrderOverviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@WebMvcTest(OrderOverviewPageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderOverviewPageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderOverviewService orderOverviewService;

    @MockBean
    private CartService cartService;

    @MockBean
    private AuthService authService;

    private OrderHeader createTestOrder(Long id) {
        return new OrderHeader(
                id,
                LocalDate.now(),
                OrderStatus.PENDING,
                BigDecimal.valueOf(130000.00),
                BigDecimal.valueOf(500.00),
                BigDecimal.valueOf(130500.00),
                "Pat",
                "Sedan",
                "Petrol-21",
                "2223111",
                "Automatic",
                new HashSet<>()
        );
    }

    @Test
    public void testGetOrderById_Success() throws Exception {
        long orderId = 1L;

        OrderHeader order = createTestOrder(orderId);

        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Camry");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(car);
        orderItem.setQuantity(2);

        Set<OrderItem> orderItems = Set.of(orderItem);
        order.setOrderItems(orderItems);

        String message = "Test message";

        when(orderOverviewService.getOrderByIdOrNull(orderId)).thenReturn(order);
        when(orderOverviewService.getMessage()).thenReturn(message);
        when(cartService.getCartCount()).thenReturn(5);
        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("200.00"));

        mockMvc.perform(get("/order-overview/" + orderId))
            .andExpect(status().isOk())
            .andExpect(view().name("overviewPage"))
            .andExpect(model().attributeExists("order"))
            .andExpect(model().attribute("orderId", orderId))
            .andExpect(model().attribute("showMsg", true))
            .andExpect(model().attribute("message", message))
            .andExpect(model().attribute("cartCount", 5))
            .andExpect(model().attribute("cartTotalCost", new BigDecimal("200.00")));

    }

    @Test
    void testGetOrderById_OrderNotFound_ShouldThrowResourceNotFoundException() throws Exception {
        long orderId = 2L;

        when(orderOverviewService.getOrderByIdOrNull(orderId)).thenReturn(null);

        mockMvc.perform(get("/order-overview/" + orderId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
