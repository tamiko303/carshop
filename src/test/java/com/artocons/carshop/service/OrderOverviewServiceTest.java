package com.artocons.carshop.service;

import com.artocons.carshop.persistence.enums.OrderStatus;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.repository.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class OrderOverviewServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private HttpSession session;

    private OrderOverviewService orderOverviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderOverviewService = new OrderOverviewService(orderRepository, session);
    }

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
    public void testGetOrderByIdOrNull_OrderExists() {
        Long orderId = 1L;
        OrderHeader expectedOrder = createTestOrder(1L);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        OrderHeader actualOrder = orderOverviewService.getOrderByIdOrNull(orderId);

        assertThat(actualOrder).isEqualTo(expectedOrder);
    }

    @Test
    public void testGetOrderByIdOrNull_OrderDoesNotExist() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        OrderHeader actualOrder = orderOverviewService.getOrderByIdOrNull(orderId);

        assertThat(actualOrder).isNull();
    }

    @Test
    public void testGetMessage_MessageExists() {
        String expectedMessage = "Your order has been placed successfully.";

        when(session.getAttribute("orderMessage")).thenReturn(expectedMessage);

        String actualMessage = orderOverviewService.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void testGetMessage_NoMessage() {
        when(session.getAttribute("orderMessage")).thenReturn(null);

        String actualMessage = orderOverviewService.getMessage();

        assertThat(actualMessage).isNull();
    }
}
