package com.artocons.carshop.persistence.repository;

import com.artocons.carshop.persistence.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
