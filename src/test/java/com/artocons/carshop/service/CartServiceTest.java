package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.util.CarShopHelper;
import com.artocons.carshop.validation.QuantityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CartServiceTest {

    @Mock
    private HttpSession session;

    @Mock
    private CartService cartService;

    @Mock
    private CarService carService;

    @Mock
    private QuantityValidator quantityValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(session, carService, quantityValidator);
    }

    private Cart createTestCart(int quantity, Long productId) {
        Cart cart = new Cart();
        cart.setQuantity(quantity);
        cart.setProduct(productId);
        return cart;
    }

    @Test
    public void testGetCartCount_EmptyCart() {

        when(session.getAttribute("cart")).thenReturn(Collections.emptyList());

        int count = cartService.getCartCount();

        assertThat(count).isEqualTo(0);
    }

    @Test
    public void testGetCartCount_NullCart() {

        when(session.getAttribute("cart")).thenReturn(null);

        int count = cartService.getCartCount();

        assertThat(count).isEqualTo(0);
    }

    @Test
    public void testGetCartCount_WithItems() {

        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(createTestCart(2, 1L));
        cartItems.add(createTestCart(3, 3L));

        when(session.getAttribute("cart")).thenReturn(cartItems);

        int count = cartService.getCartCount();

        assertThat(count).isEqualTo(5); // 2 + 3 = 5
    }

    @Test
    public void testGetCartTotalCost_EmptyCart() throws ResourceNotFoundException {
        when(session.getAttribute("cart")).thenReturn(Collections.emptyList());

        BigDecimal totalCost = cartService.getCartTotalCost();

        assertThat(totalCost).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void testGetCartTotalCost_NullCart() throws ResourceNotFoundException {
        when(session.getAttribute("cart")).thenReturn(null);

        BigDecimal totalCost = cartService.getCartTotalCost();

        assertThat(totalCost).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void testGetCartTotalCost_WithItems() throws ResourceNotFoundException {
        List<Cart> cartItems = new ArrayList<>();

        cartItems.add(createTestCart(2, 1L));
        cartItems.add(createTestCart(3, 2L));

        when(session.getAttribute("cart")).thenReturn(cartItems);

        when(carService.getPriceById(1L)).thenReturn(BigDecimal.valueOf(10.00));
        when(carService.getPriceById(2L)).thenReturn(BigDecimal.valueOf(20.00));

        BigDecimal totalCost = cartService.getCartTotalCost();

        BigDecimal expectedTotal = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(10.00))
                .add(BigDecimal.valueOf(3).multiply(BigDecimal.valueOf(20.00)));
        assertThat(totalCost).isEqualTo(expectedTotal); // 20.00 + 60.00 = 80.00
    }

    @Test
    public void testRemoveProductFromCart_ProductExists() throws ResourceNotFoundException {
        List<Cart> cartItems = new ArrayList<>();
        Long productIdToRemove = 1L;

        cartItems.add(createTestCart(2, productIdToRemove));
        cartItems.add(createTestCart(3, 2L));

        when(session.getAttribute("cart")).thenReturn(cartItems);

        CarShopHelper helper = mock(CarShopHelper.class);
        when(helper.getCartItemByProductId(productIdToRemove)).thenReturn(Optional.of(cartItems.get(0)));

        cartService.removeProductFromCart(productIdToRemove);

        assertThat(cartItems).hasSize(1);
        assertThat(cartItems.get(0).getProduct()).isEqualTo(2L);

//        verify(session).setAttribute("cart", cartItems);
    }

    @Test
    public void testRemoveProductFromCart_ProductDoesNotExist() throws ResourceNotFoundException {
        List<Cart> cartItems = new ArrayList<>();

        cartItems.add(createTestCart(3, 2L));

        when(session.getAttribute("cart")).thenReturn(cartItems);

        Long productIdToRemove = 1L;

        CarShopHelper helper = mock(CarShopHelper.class);
        when(helper.getCartItemByProductId(productIdToRemove)).thenReturn(Optional.empty());

        cartService.removeProductFromCart(productIdToRemove);

        assertThat(cartItems).hasSize(1);

        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    public void testClearCart() {
        cartService.clearCart();

        verify(session).removeAttribute("cart");
    }

    @Test
    public void testGetCartList_EmptyCart() {
        when(session.getAttribute("cart")).thenReturn(null);

        List<Cart> cartList = cartService.getCartList();

        assertThat(cartList).hasSize(1);
        assertThat(cartList.get(0)).isNotNull();
    }

    @Test
    public void testGetCartList_NonEmptyCart() {
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart()); // Добавляем один товар

        when(session.getAttribute("cart")).thenReturn(cartItems);

        List<Cart> cartList = cartService.getCartList();

        assertThat(cartList).isEqualTo(cartItems);
    }

//    @Test
//    public void testAddItemToCart_EmptyCart() throws Exception {
//        when(session.getAttribute("cart")).thenReturn(null);
//
//        Cart newCartItem = new Cart();
//        newCartItem.setProduct(1L);
//        newCartItem.setQuantity(2);
//
//        doNothing().when(quantityValidator).validate(newCartItem);
//
//        ResultData result = cartService.addItemToCart(newCartItem);

//        verify(session).setAttribute("cart", List.of(newCartItem));
//        assertThat(result.getCount()).isEqualTo(2);
//    }
}
