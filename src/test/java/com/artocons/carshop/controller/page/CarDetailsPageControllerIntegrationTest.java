package com.artocons.carshop.controller.page;

import com.artocons.carshop.CarShopApplication;
import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.repository.CarRepository;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
//        classes = CarDetailsPageController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarDetailsPageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class CarServiceImplTestContextConfiguration {
        @Bean
        public CarService carService() {
            return new CarService(null, null) {
                // implement methods
            };
        }
        @Bean
        public CartService cartService() {
            return new CartService(null, null, null) {
                // implement methods
            };
        }
    }

    @MockBean
    private CarService carService;

    @Autowired
    private CartService cartService;

    private Car firstProduct;

    @BeforeEach
    public void setUp() throws ResourceNotFoundException {
        firstProduct = new Car(
                1L,
                "Test Brand",
                "Test Model",
                "Description of the car",
                new BigDecimal("20000.00"),
                2020L,
                15000L,
                "Sedan",
                "Petrol",
                "2.0L",
                "Automatic",
                new HashSet<>()
        );

        when(carService.getCarById(firstProduct.getId()))
                .thenReturn(firstProduct);

        when(cartService.getCartCount()).thenReturn(3);
        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("150000.00"));
    }

    @Test
    public void testGetCarDetails() throws Exception {
        mockMvc.perform(get("/cars/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("carDetailsPage"))
                .andExpect(model().attributeExists("carItem"))
                .andExpect(model().attribute("carItem", firstProduct))
                .andExpect(model().attribute("cartCount", 3))
                .andExpect(model().attribute("cartTotalCost", new BigDecimal("150000.00")));

        verify(carService).getCarById(1L);
        verify(cartService).getCartCount();
        verify(cartService).getCartTotalCost();
    }

    @Test
    public void testGetCarDetails_CarNotFound() throws Exception {
        when(carService.getCarById(anyLong())).thenThrow(new NoSuchElementException("Car not found"));

        mockMvc.perform(get("/cars/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(carService).getCarById(999L);
    }

    @Test
    public void testGoBack_WithReferer() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("previousCarPage", "/previous-car");

        mockMvc.perform(get("/cars/goBack").session(session))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/previous-car"));

        verify(session).getAttribute("previousCarPage");
    }

    @Test
    public void testGoBack_WithoutReferer() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/cars/goBack").session(session))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/cars"));

        verify(session).getAttribute("previousCarPage");
    }
}
