package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.request.OrderRequest;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderPageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private CartService cartService;

    private BigDecimal deliveryPrice = new BigDecimal("5.00");

    @BeforeEach
    public void setUp() throws ResourceNotFoundException, ResourceVaidationException {
        // Настройка моков перед каждым тестом
        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("100.00"));
        when(cartService.getCartCount()).thenReturn(2);
        when(orderService.showOrderPage(any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(orderService.placeOrder(any())).thenReturn(new OrderHeader());
    }

    @Test
    public void testShowOrder() throws Exception {
        mockMvc.perform(get("/order").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("orderPage"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attribute("subTotal", new BigDecimal("100.00")))
                .andExpect(model().attribute("delivery", deliveryPrice))
                .andExpect(model().attribute("total", new BigDecimal("105.00")))
                .andExpect(model().attribute("cartCount", 2))
                .andExpect(model().attribute("cartTotalCost", new BigDecimal("100.00")))
                .andExpect(model().attribute("isAdmin", false));

        verify(orderService).showOrderPage(any());
        verify(cartService).getCartTotalCost();
        verify(cartService).getCartCount();
    }

    @Test
    public void testGoBack_WithReferer() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("previousCartPage", "/previous-cart");

        mockMvc.perform(get("/order/goBack")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/previous-cart"));

        verify(session).getAttribute("previousCartPage");
    }

    @Test
    public void testGoBack_WithoutReferer() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/order/goBack")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        verify(session).getAttribute("previousCartPage");
    }

    @Test
    public void testPlaceOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest();

        mockMvc.perform(post("/order/placeOrder")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("field1", "value1")
                        .param("field2", "value2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order-overview/1"));

        verify(orderService).placeOrder(any(OrderRequest.class));
    }
}
