package com.oliviatodesco.save_food.repository;

import com.oliviatodesco.save_food.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {

}
