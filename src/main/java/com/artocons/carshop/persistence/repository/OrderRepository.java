package com.artocons.carshop.persistence.repository;

import com.artocons.carshop.persistence.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
