package com.oliviatodesco.save_food.repository;


import com.oliviatodesco.save_food.model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserSec, Long> {

    Optional<UserSec> findUserEntityByUsername(String username);
    Optional<UserSec> findByEmail(String email);
}
