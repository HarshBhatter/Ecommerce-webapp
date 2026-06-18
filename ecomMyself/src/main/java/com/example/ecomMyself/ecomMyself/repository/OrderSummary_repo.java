package com.example.ecomMyself.ecomMyself.repository;

import com.example.ecomMyself.ecomMyself.model.OrderSummary;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderSummary_repo extends JpaRepository<OrderSummary,Long> {
    Optional<OrderSummary> findByUser(Users user);

    void deleteAllByUser(Users user);

    boolean existsByUser(Users user);
}
