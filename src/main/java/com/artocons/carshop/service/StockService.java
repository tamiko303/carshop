package com.artocons.carshop.service;

import com.artocons.carshop.exception.ResourceNotFoundException;
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
//                .filter(i -> i.getStock() > i.getReserved() )
                .filter(i -> i.getStock() > 0  )
                .collect(Collectors.toList());
    }

    public Stock getStocksByCarId(Long id) throws ResourceNotFoundException {
        return stockRepository.findById(id)
                              .orElseThrow(() -> new ResourceNotFoundException("Stock not found for carId :: " + id));
    }

    @Transactional
    public void reserveStock(Long stockId, int amountToReserve) throws ResourceNotFoundException {
        Stock stock = stockRepository.findById(stockId)
                                     .orElseThrow(() -> new ResourceNotFoundException("Stock not found for id :: " + stockId));

        if (stock.getStock() < amountToReserve) {
            throw new IllegalArgumentException("Not enough available stock to reserve");
        }

        stock.setReserved(stock.getReserved() + amountToReserve);
        stock.setStock(stock.getStock() - amountToReserve);

        stockRepository.save(stock);
    }
}
