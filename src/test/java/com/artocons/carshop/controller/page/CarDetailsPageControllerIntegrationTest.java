package com.artocons.carshop.controller.page;

import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.service.AuthService;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarDetailsPageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CarDetailsPageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private CartService cartService;

    @MockBean
    private AuthService authService;

    private Car createTestCar(Long id, String brand, String model, BigDecimal expectedPrice) {
        return new Car(
                id,
                brand,
                model,
                "Description of the car",
                expectedPrice,
                2020L,
                15000L,
                "Sedan",
                "Petrol",
                "2.0L",
                "Automatic",
                new HashSet<>()
        );
    }

    @Test
    public void testGetCarDetails_Success() throws Exception {
        Long productId = 1L;

        Car firstProduct = createTestCar(productId, "Toyota", "Test Model",  BigDecimal.valueOf(10000));

        when(carService.getCarById(productId)).thenReturn(firstProduct);
        when(cartService.getCartCount()).thenReturn(5);
        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("12345.67"));


        mockMvc.perform(get("/product/" + productId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("carDetailsPage"))
                .andExpect(model().attributeExists("carItem"))
                .andExpect(model().attribute("carItem", firstProduct))
                .andExpect(model().attribute("cartCount", 5))
                .andExpect(model().attribute("cartTotalCost", new BigDecimal("12345.67")));

        verify(carService).getCarById(1L);
        verify(cartService).getCartCount();
        verify(cartService).getCartTotalCost();
    }

    @Test
    public void testGetCarDetails_CarNotFound() throws Exception {
        when(carService.getCarById(anyLong())).thenThrow(new NoSuchElementException("Car not found"));

        mockMvc.perform(get("/product/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(carService).getCarById(999L);
    }

    @Test
    public void testGoBack_WithReferer() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("previousCarPage", "/cars?page=2");

        mockMvc.perform(get("/product/goBack").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cars?page=2"));
    }

    @Test
    public void testGoBack_WithoutReferer() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/product/goBack").session(session))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/cars"));
    }
}
