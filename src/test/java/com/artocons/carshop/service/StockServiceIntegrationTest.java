package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.exception.ResourceNotFoundException;
import com.artocons.carshop.persistence.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class StockServiceIntegrationTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    private Stock stock1;
    private Stock stock2;

    @BeforeEach
    void setUp() {
        stockRepository.deleteAll();

        stock1 = new Stock();
        stock1.setProductId(1L);
        stock1.setStock(10L);
        stock1.setReserved(2L);

        stock2 = new Stock();
        stock2.setProductId(2L);
        stock2.setStock(2L);
        stock2.setReserved(2L);

        stockRepository.save(stock1);
        stockRepository.save(stock2);
    }

    @Test
    void testGetAllStocks() {
        Page<Stock> page = stockService.getAllStocks(PageRequest.of(0, 10));

        assertThat(page).isNotNull();
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent()).extracting("productId").containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    void testGetAllAvailableCarsId() {
        List<Stock> availableStocks = stockService.getAllAvailableCarsId();

        assertThat(availableStocks).hasSize(2);
        assertThat(availableStocks.get(0).getProductId()).isEqualTo(stock1.getProductId());
    }

    @Test
    void testGetStocksByCarIdFound() throws ResourceNotFoundException {
        Stock found = stockService.getStocksByCarId(stock1.getProductId());

        assertThat(found).isNotNull();
        assertThat(found.getProductId()).isEqualTo(stock1.getProductId());
    }

    @Test
    void testGetStocksByCarIdNotFound() {
        Long nonExistentId = 999L;

        assertThrows(ResourceNotFoundException.class,
                () -> stockService.getStocksByCarId(nonExistentId));
    }

    @Test
    void testReserveStockSuccess() throws ResourceNotFoundException {
        int amountToReserve = 5;

        Stock beforeReserve = stockRepository.findById(stock1.getProductId()).orElseThrow();
        Long initialStock = beforeReserve.getStock();
        Long initialReserved = beforeReserve.getReserved();

        stockService.reserveStock(stock1.getProductId(), amountToReserve);

        Stock afterReserve = stockRepository.findById(stock1.getProductId()).orElseThrow();

        assertThat(afterReserve.getStock()).isEqualTo(initialStock - amountToReserve);
        assertThat(afterReserve.getReserved()).isEqualTo(initialReserved + amountToReserve);
    }

    @Test
    void testReserveStockNotEnoughStock() {
        int amountToReserve = 20;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> stockService.reserveStock(stock1.getProductId(), amountToReserve));

        assertThat(exception).hasMessageContaining("Not enough available stock");
    }

    @Test
    void testReserveStockNotFound() {
        Long nonExistentId = 999L;

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> stockService.reserveStock(nonExistentId, 5));

        assertThat(exception).hasMessageContaining("Stock not found for id");
    }
}