package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.repository.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Resource
    public StockRepository stockRepository;

    public Page<Stock> getAllStocks(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }

    public List<Stock> getAllAvailableCarsId() {

        return stockRepository.findAll(Pageable.unpaged()).getContent()
                .stream()
                .filter(i -> i.getStock() > i.getReserved() )
                .collect(Collectors.toList());
    }

    @Transactional
    public void reserveStock(Long stockId, int amountToReserve) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found"));

        if (stock.getStock() < amountToReserve) {
            throw new IllegalArgumentException("Not enough available stock to reserve");
        }

        stock.setReserved(stock.getReserved() + amountToReserve);
        stock.setStock(stock.getStock() - amountToReserve);

        stockRepository.save(stock);
    }
}
