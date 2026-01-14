package com.example.ecomMyself.ecomMyself.repository;

import com.example.ecomMyself.ecomMyself.model.Product_size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface Product_size_repo extends JpaRepository<Product_size,Integer> {
    @Modifying
    @Query("""
        UPDATE Product_size ps
        SET ps.quantity = ps.quantity - :qty
        WHERE ps.size = :size
        AND ps.productColors.color = :color
        AND ps.productColors.product.id = :productId
        AND ps.quantity >= :qty
    """)
    int reduceStock(
            int productId,
            String color,
            int size,
            int qty
    );

    Optional<Product_size> findBySizeAndProductColors_ColorAndProductColors_Product_Id(int size, String color, int productid);
}
