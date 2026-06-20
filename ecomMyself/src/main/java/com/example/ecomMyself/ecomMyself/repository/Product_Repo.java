package com.example.ecomMyself.ecomMyself.repository;

import com.example.ecomMyself.ecomMyself.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface Product_Repo extends JpaRepository<Product,Integer> {
    Page<Product> findAllByGender(String gender,Pageable pageable);

//    boolean existsByNameAndTypeAndFitAndPriceAndDescriptionAndGender(String name, String type, String fit, BigDecimal price, String description, String gender);

    Optional<Product> findByNameAndTypeAndFitAndPriceAndDescriptionAndGender(String name, String type, String fit, BigDecimal price, String description, String gender);

    Page<Product> findAll(Pageable pageable);
}
