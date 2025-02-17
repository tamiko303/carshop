package com.artocons.carshop.persistence.repository;

import com.artocons.carshop.persistence.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
