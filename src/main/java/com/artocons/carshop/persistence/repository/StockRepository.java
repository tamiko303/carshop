package com.artocons.carshop.persistence.repository;

import com.artocons.carshop.persistence.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
