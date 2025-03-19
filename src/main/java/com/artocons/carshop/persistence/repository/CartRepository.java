package com.artocons.carshop.persistence.repository;

import com.artocons.carshop.persistence.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
