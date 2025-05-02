package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.repository.CarRepository;
import com.artocons.carshop.persistence.repository.StockRepository;
import com.artocons.carshop.util.ProductTestHelper;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CartServiceIntegrationTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CarService carService;

    @Autowired
    private HttpSession session;

    private List<Cart> cartList;

    @BeforeEach
    public void setUp() {
        cartList = new ArrayList<>();
        session.setAttribute("cart", cartList);

    }

    @Test
    public void testAddItemToCart_NewItem() throws ServiceException, ResourceNotFoundException, ResourceVaidationException {
        Cart newCartItem = new Cart();
        newCartItem.setProduct(1L);
        newCartItem.setQuantity(2);

        cartService.addItemToCart(newCartItem);

        assertEquals(1, cartList.size());
        assertEquals(2, cartList.get(0).getQuantity());
    }

    @Test
    public void testGetCartCount() {
        Cart cartItem1 = new Cart();
        cartItem1.setQuantity(2);

        Cart cartItem2 = new Cart();
        cartItem2.setQuantity(3);

        cartList.add(cartItem1);
        cartList.add(cartItem2);

        int count = cartService.getCartCount();

        assertEquals(5, count);
    }

    @Test
    public void testGetCartTotalCost() throws ResourceNotFoundException {
        Car product1 = ProductTestHelper.createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000));
        Car product2 = ProductTestHelper.createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));

        carRepository.save(product1);
        carRepository.save(product2);

        Stock stock1 = new Stock();
        stock1.setProductId(product1.getId());
        stock1.setStock(10L);
        stock1.setReserved(2L);

        Stock stock2 = new Stock();
        stock2.setProductId(product2.getId());
        stock2.setStock(5L);
        stock2.setReserved(2L);

        stockRepository.save(stock1);
        stockRepository.save(stock2);

        Cart cartItem1 = new Cart();
        cartItem1.setProduct(product1.getId());
        cartItem1.setQuantity(2);

        Cart cartItem2 = new Cart();
        cartItem2.setProduct(product2.getId());
        cartItem2.setQuantity(3);

        cartList.add(cartItem1);
        cartList.add(cartItem2);

        BigDecimal totalCost = cartService.getCartTotalCost();

        assertEquals(BigDecimal.valueOf(70000), totalCost); // 2 * 20000 + 3 * 10000 = 80000
    }

    @Test
    public void testRemoveProductFromCart() throws ResourceNotFoundException {
        Cart existingCart = new Cart();
        existingCart.setProduct(1L);
        existingCart.setQuantity(1);

        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(existingCart);
        session.setAttribute("cart",  cartItems);

        cartService.removeProductFromCart(existingCart.getProduct());

        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");

        assertTrue(cartList.isEmpty());
    }

    @Test
    public void testClearCart() {
        Cart existingCart = new Cart();
        existingCart.setProduct(1L);
        existingCart.setQuantity(1);

        session.setAttribute("cart", List.of(existingCart));

        cartService.clearCart();

        assertNull(session.getAttribute("cart"));
    }
}
