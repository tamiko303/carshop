package com.artocons.carshop.persistence.repository;

import com.artocons.carshop.persistence.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
