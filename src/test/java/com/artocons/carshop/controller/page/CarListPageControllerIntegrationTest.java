package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.OrderItem;
import com.artocons.carshop.persistence.repository.CarRepository;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.util.ProductTestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarListPageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthService authService;
    @Autowired
    private CarRepository carRepository;

    @Test
    public void testFindAvailableCarsList() throws Exception {

        Car product2 = ProductTestHelper.createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));
        carRepository.save(product2);

        mockMvc.perform(get("/cars/page/1")
                        .param("query", "Honda"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddToCart() throws Exception {
        carRepository.deleteAll();
        Car product2 = ProductTestHelper.createTestCar(2L, "Honda", "Accord",  BigDecimal.valueOf(10000));
        carRepository.save(product2);

        mockMvc.perform(post("/cars/{productId}/addToCart",product2.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("quantity", "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddToCart_ProductNotFound() throws Exception {
        long productId = 9999L;

        mockMvc.perform(post("/cars/{productId}/addToCart", productId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("quantity", "2"))
                .andExpect(status().isNotFound());
    }
}