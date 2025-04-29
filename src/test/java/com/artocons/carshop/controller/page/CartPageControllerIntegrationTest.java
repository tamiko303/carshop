package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.dtos.CartItemDTO;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class CartPageControllerIntegrationTest {

    private final MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private CartPageController cartPageController;

    @BeforeEach
    public void setUp() throws ResourceNotFoundException {
        when(cartService.getCartCount()).thenReturn(3);
        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("150.00"));
    }

    @Test
    public void testGetAllItemsCart() throws Exception {
        Page<CartItemDTO> cartItems = new PageImpl<>(new ArrayList<>());
        when(cartService.getCartPage(any())).thenReturn((PageImpl<CartItemDTO>) cartItems);

        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cartPage"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(model().attribute("cartCount", 3))
                .andExpect(model().attribute("cartTotalCost", new BigDecimal("150.00")));

        verify(cartService).getCartPage(any());
        verify(cartService).getCartCount();
        verify(cartService).getCartTotalCost();
    }
}
