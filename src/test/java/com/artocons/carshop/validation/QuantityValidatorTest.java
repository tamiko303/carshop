package com.artocons.carshop.validation;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.exception.ResourceVaidationException;
import com.artocons.carshop.persistence.model.Cart;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class QuantityValidatorTest {

    @Mock
    private StockService stockService;

    private QuantityValidator quantityValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        quantityValidator = new QuantityValidator(stockService);
    }

    @Test
    public void testValidate_ValidQuantity() throws ResourceNotFoundException, ResourceVaidationException {
        Cart cart = new Cart();
        cart.setProduct(1L);
        cart.setQuantity(5);

        Stock stock = new Stock();
        stock.setProductId(1L);
        stock.setStock(10L);

        when(stockService.getStocksByCarId(cart.getProduct())).thenReturn(stock);

        quantityValidator.validate(cart);

        verify(stockService).getStocksByCarId(cart.getProduct());
    }

    @Test
    public void testValidate_InsufficientStock() throws ResourceNotFoundException {
        Cart cart = new Cart();
        cart.setProduct(1L);
        cart.setQuantity(15);

        Stock stock = new Stock();
        stock.setProductId(1L);
        stock.setStock(10L);

        when(stockService.getStocksByCarId(cart.getProduct())).thenReturn(stock);

        assertThrows(ResourceVaidationException.class, () -> {
            quantityValidator.validate(cart);
        });

        verify(stockService).getStocksByCarId(cart.getProduct());
    }

    @Test
    public void testValidate_ProductNotFound() throws ResourceNotFoundException {
        Cart cart = new Cart();
        cart.setProduct(1L);

        when(stockService.getStocksByCarId(cart.getProduct())).thenThrow(new ResourceNotFoundException("Product not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            quantityValidator.validate(cart);
        });

        verify(stockService).getStocksByCarId(cart.getProduct());
    }
}
