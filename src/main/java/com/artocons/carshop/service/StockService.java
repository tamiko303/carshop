package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Stock;
import com.artocons.carshop.persistence.repository.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
