package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.dtos.OrderItemDTO;
import com.artocons.carshop.persistence.model.OrderHeader;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.request.OrderRequest;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderPageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderPageControllerIntegrationTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private OrderService orderService;
//
//    @MockBean
//    private CartService cartService;
//
//    @MockBean
//    private AuthService authService;
//
//    private BigDecimal deliveryPrice = new BigDecimal("5.00");
//
//    @BeforeEach
//    public void setUp() throws ResourceNotFoundException, ResourceVaidationException {
//        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("100.00"));
//        when(cartService.getCartCount()).thenReturn(2);
//        when(orderService.showOrderPage(any())).thenReturn(new PageImpl<>(new ArrayList<>()));
//        when(orderService.placeOrder(any())).thenReturn(new OrderHeader());
//    }
//
//    @Test
//    void testShowOrder() throws Exception {
//        OrderItem item = mock(OrderItem.class);
//        Page<OrderItem> page = new PageImpl<>(List.of(item));
//
//        when(orderService.showOrderPage(Pageable.unpaged())).thenReturn((PageImpl<OrderItemDTO>) page);
//
//        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("100.00"));
//        when(cartService.getCartCount()).thenReturn(3);
//
//        mockMvc.perform(get("/order"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("orderPage"))
//                .andExpect(model().attributeExists("order"))
//                .andExpect(model().attributeExists("subTotal"))
//                .andExpect(model().attributeExists("delivery"))
//                .andExpect(model().attributeExists("total"))
//                .andExpect(model().attributeExists("cartCount"))
//                .andExpect(model().attributeExists("cartTotalCost"))
//                .andExpect(model().attributeExists("isAdmin"));
//
//        verify(orderService).showOrderPage(Pageable.unpaged());
//        verify(cartService, times(2)).getCartTotalCost(); // вызывается дважды в контроллере
//        verify(cartService).getCartCount();
//    }
//
//    @Test
//    public void testGoBack_WithReferer() throws Exception {
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("previousCartPage", "/cart");
//
//        mockMvc.perform(get("/order/goBack")
//                        .session(session))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/cart"));
//    }
//
//    @Test
//    public void testGoBack_WithoutReferer() throws Exception {
//        MockHttpSession session = new MockHttpSession();
//
//        mockMvc.perform(get("/order/goBack")
//                        .session(session))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/cart"));
//    }
//
//    @Test
//    public void testPlaceOrder_Success() throws Exception {
//        OrderHeader savedOrder = new OrderHeader();
//        savedOrder.setOrderId(123L);
//
//        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(savedOrder);
//
//        mockMvc.perform(post("/order/placeOrder")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/order-overview/123"));
//
//        verify(orderService).placeOrder(any(OrderRequest.class));
//    }
}
