package com.artocons.carshop.controller.page;
import com.artocons.carshop.persistence.model.*;
import com.artocons.carshop.persistence.repository.CarRepository;
import com.artocons.carshop.persistence.repository.OrderRepository;
import com.artocons.carshop.persistence.repository.StockRepository;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.util.OrderTestHelper;
import com.artocons.carshop.util.ProductTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class CartPageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private HttpSession session;

    @Autowired
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        orderRepository.deleteAll();
        carRepository.deleteAll();
        stockRepository.deleteAll();
        session.invalidate();

        Set<OrderItem> orderItems = new HashSet<>();

        Car product1 = ProductTestHelper.createTestCar(1L, "Toyota", "Camry",  BigDecimal.valueOf(20000));
        carRepository.save(product1);

        OrderItem item1 = new OrderItem(product1.getId(), 2);

        Car product2 = ProductTestHelper.createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));
        carRepository.save(product2);

        OrderItem item2 = new OrderItem(product2.getId(), 3);

        orderItems.add(item1);
        orderItems.add(item2);

        OrderHeader savedOrder = OrderTestHelper.createTestOrder(1L, orderItems);

        orderRepository.save(savedOrder);

        session.setAttribute("order", savedOrder);

        Stock stock1 = new Stock();
        stock1.setProductId(product1.getId());
        stock1.setStock(10L);
        stock1.setReserved(2L);

        Stock stock2 = new Stock();
        stock2.setProductId(product2.getId());
        stock2.setStock(2L);
        stock2.setReserved(2L);

        stockRepository.save(stock1);
        stockRepository.save(stock2);

        Cart cartItem1 = new Cart();
        cartItem1.setProduct(product1.getId());
        cartItem1.setQuantity(2);
        Cart cartItem2 = new Cart();
        cartItem2.setProduct(product2.getId());
        cartItem2.setQuantity(1);

        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        session.setAttribute("cart", cartItems);
    }

    @Test
    public void testGetAllItemsCart() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cartPage"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(model().attributeExists("cartCount"))
                .andExpect(model().attributeExists("cartTotalCost"))
                .andExpect(model().attributeExists("isAdmin"));
    }

    @Test
    public void testRemoveProductFromCart() throws Exception {
        long productId = 1L;

        mockMvc.perform(post("/cart/{productId}/remove", productId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cart"));
    }
}
