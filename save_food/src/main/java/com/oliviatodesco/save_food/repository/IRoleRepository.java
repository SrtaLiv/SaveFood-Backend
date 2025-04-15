package com.oliviatodesco.save_food.repository;

import com.oliviatodesco.save_food.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
        Role findByRole(String role);
}