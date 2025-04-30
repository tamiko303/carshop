package com.artocons.carshop.controller.page;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.Car;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.ResultData;
import com.artocons.carshop.persistence.request.AjaxRequest;
import com.artocons.carshop.service.CarService;
import com.artocons.carshop.service.CartService;
import com.artocons.carshop.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CarListPageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CarListPageControllerIntegrationTest {

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

//    @WithMockUser(username = "Admin", roles = {"ROLE_ADMIN"})
    @Test
    public void testFindAvailableCarsList() throws Exception {

        Car car = createTestCar(1L, "Toyota", "Test Model",  BigDecimal.valueOf(10000));

        Page<Car> carPage = new PageImpl<>(Collections.singletonList(car), Pageable.unpaged(), 1);

        when(carService.getAvailableCarsList(anyString(), anyInt(), anyString(), anyString(), any(Pageable.class)))
                .thenReturn(carPage);

        when(cartService.getCartList()).thenReturn(Collections.emptyList());
        when(cartService.getCartCount()).thenReturn(3);
        when(cartService.getCartTotalCost()).thenReturn(new BigDecimal("30000"));

        mockMvc.perform(get("/cars/page/1")
                        .param("query", "test")
                        .param("sortField", "price")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("carListPage"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attributeExists("query"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("sortField"))
                .andExpect(model().attributeExists("sortDir"))
                .andExpect(model().attributeExists("reverseSortDir"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(model().attributeExists("cartCount"))
                .andExpect(model().attributeExists("cartTotalCost"))

                .andExpect(model().attribute("query", "test"))
                .andExpect(model().attribute("currentPage", 1))
                .andExpect(model().attribute("totalPages", 1))
                .andExpect(model().attribute("sortField", "price"))
                .andExpect(model().attribute("sortDir", "asc"))
                .andExpect(model().attribute("reverseSortDir", "desc"))
                .andExpect(model().attribute("cartCount", 3))
                .andExpect(model().attribute("cartTotalCost", new BigDecimal("30000")));
    }

    @Test
    void addToCart_Success() throws Exception {
        long productId = 1L;

        ResultData resultData = new ResultData();

        when(cartService.addItemToCart(any(Cart.class))).thenReturn(resultData);

        AjaxRequest request = new AjaxRequest( 2 );

        mockMvc.perform(post("/cars/" + productId + "/addToCart")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("quantity", String.valueOf(request.getQuantity()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.msg").isEmpty())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void addToCart_ResourceNotFound() throws Exception {
        long productId = 1L;

        when(cartService.addItemToCart(any(Cart.class)))
                .thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(post("/cars/" + productId + "/addToCart")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("quantity", "1")
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.msg").value("Product not found"));
    }

    @Test
    void addToCart_ResourceValidationException() throws Exception {
        long productId = 1L;

        when(cartService.addItemToCart(any(Cart.class)))
                .thenThrow(new ResourceVaidationException("Invalid quantity"));

        mockMvc.perform(post("/cars/" + productId + "/addToCart")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("quantity", "0")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("422"))
                .andExpect(jsonPath("$.msg").value("Invalid quantity"));
    }

}
