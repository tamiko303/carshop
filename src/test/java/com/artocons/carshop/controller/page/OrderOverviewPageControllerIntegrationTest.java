package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.OrderOverviewService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

import static org.h2.store.fs.FilePath.get;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class OrderOverviewPageControllerIntegrationTest {

    private final MockMvc mockMvc;

    @Mock
    private OrderOverviewService orderOverviewService;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderOverviewPageController orderOverviewPageController;

    @BeforeEach
    public void setUp() throws ResourceNotFoundException {
        OrderHeader mockOrderHeader = new OrderHeader();
        mockOrderHeader.setOrderItems((Set<OrderItem>) new ArrayList<OrderItem>());

        when(orderOverviewService.getOrderByIdOrNull(anyLong())).thenReturn(mockOrderHeader);
        when(orderOverviewService.getMessage()).thenReturn("Test message");
        when(cartService.getCartCount()).thenReturn(5);
        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("200.00"));
    }
    @Test
    public void testGetOrderById() throws Exception {
        long orderId = 1L;

        mockMvc.perform((RequestBuilder) get("/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(view().name("overviewPage"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attribute("orderId", orderId))
                .andExpect(model().attribute("showMsg", true))
                .andExpect(model().attribute("message", "Test message"))
                .andExpect(model().attribute("cartCount", 5))
                .andExpect(model().attribute("cartTotalCost", new BigDecimal("200.00")));

        verify(orderOverviewService).getOrderByIdOrNull(orderId);
        verify(orderOverviewService).getMessage();
        verify(cartService).getCartCount();
        verify(cartService).getCartTotalCost();
    }

}
