package com.artocons.carshop.persistence.repository;

import com.artocons.carshop.persistence.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT p FROM Car p WHERE " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%'))" +
            " Or LOWER(p.model) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Car> searchByBrandOrModel(String query, Pageable pageable);
}
