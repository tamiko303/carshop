package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Car;
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
    private StockRepository stockRepositoryRepository;

    public Page<Stock> getAllCarsAvailable(Pageable pageable) {

        return StockRepository.findAll(pageable);
    }
}
