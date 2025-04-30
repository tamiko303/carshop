package com.artocons.carshop.controller.page;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.dtos.CartItemDTO;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartPageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CartPageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setUp() throws ResourceNotFoundException {
        when(cartService.getCartCount()).thenReturn(3);
        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("150.00"));
    }

    @Test
    void testGetAllItemsCart() throws Exception {
        CartItemDTO item = new CartItemDTO();
        // Заполни поля item, если нужно

        Page<CartItemDTO> page = new PageImpl<>(List.of(item));

        when(cartService.getCartPage(Pageable.unpaged())).thenReturn((PageImpl<CartItemDTO>) page);
        when(cartService.getCartCount()).thenReturn(5);
        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("250.00"));
        when(authService.getIsAdmin()).thenReturn(false);

        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cartPage"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(model().attributeExists("cartCount"))
                .andExpect(model().attributeExists("cartTotalCost"))
                .andExpect(model().attributeExists("isAdmin"));

        verify(cartService).getCartPage(Pageable.unpaged());
        verify(cartService).getCartCount();
        verify(cartService).getCartTotalCost();
        verify(authService).getIsAdmin();
    }

    @Test
    void testRemoveProductFromCart() throws Exception {
        long productId = 10L;

        doNothing().when(cartService).removeProductFromCart(productId);

        mockMvc.perform(post("/cart/" + productId + "/remove")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        verify(cartService).removeProductFromCart(productId);
    }
}
