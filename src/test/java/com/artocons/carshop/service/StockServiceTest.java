package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    private StockService stockService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stockService = new StockService(stockRepository);
    }

    @Test
    public void testGetAllStocks() {
        Page<Stock> stockPage = mock(Page.class);
        when(stockRepository.findAll(any(Pageable.class))).thenReturn(stockPage);

        Page<Stock> result = stockService.getAllStocks(Pageable.unpaged());

        assertEquals(stockPage, result);
        verify(stockRepository).findAll(any(Pageable.class));
    }

    @Test
    public void testGetAllAvailableCarsId() {
        Stock availableStock = new Stock();
        availableStock.setStock(5L);

        Stock unavailableStock = new Stock();
        unavailableStock.setStock(0L);

        when(stockRepository.findAll(Pageable.unpaged())).thenReturn(new PageImpl<>(Arrays.asList(availableStock, unavailableStock)));

        List<Stock> result = stockService.getAllAvailableCarsId();

        assertEquals(1, result.size());
        assertEquals(availableStock, result.get(0));
    }

    @Test
    public void testGetStocksByCarId_Exists() throws ResourceNotFoundException {
        Long carId = 1L;

        Stock stock = new Stock();

        when(stockRepository.findById(carId)).thenReturn(Optional.of(stock));

        Stock result = stockService.getStocksByCarId(carId);

        assertEquals(stock, result);
    }

    @Test
    public void testGetStocksByCarId_NotFound() {
        Long carId = 1L;

        when(stockRepository.findById(carId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            stockService.getStocksByCarId(carId);
        });

        assertEquals("Stock not found for carId :: " + carId, exception.getMessage());
    }

    @Test
    public void testReserveStock_Success() throws ResourceNotFoundException {
        Long stockId = 1L;
        int amountToReserve = 3;

        Stock stock = new Stock();
        stock.setStock(10L);
        stock.setReserved(0L);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        stockService.reserveStock(stockId, amountToReserve);

        assertEquals(7, stock.getStock());
        assertEquals(3, stock.getReserved());

        verify(stockRepository).save(stock);
    }

    @Test
    public void testReserveStock_NotFound() {
        Long stockId = 1L;
        int amountToReserve = 3;

        when(stockRepository.findById(stockId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            stockService.reserveStock(stockId, amountToReserve);
        });

        assertEquals("Stock not found for id :: " + stockId, exception.getMessage());
    }

    @Test
    public void testReserveStock_NotEnoughAvailable() throws ResourceNotFoundException {
        Long stockId = 1L;
        int amountToReserve = 15;

        Stock stock = new Stock();
        stock.setStock(10L);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            stockService.reserveStock(stockId, amountToReserve);
        });

        assertEquals("Not enough available stock to reserve", exception.getMessage());
    }
}
