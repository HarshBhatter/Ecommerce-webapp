package com.example.ecomMyself.ecomMyself.repository;

import com.example.ecomMyself.ecomMyself.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Cart_repo extends JpaRepository<Cart,Integer> {

    Cart findByUserIdAndProductIdAndColorAndSize(int userId,int productid,String color,int size);
    @Query("""
            SELECT c
            FROM Cart c
            JOIN FETCH c.product
            WHERE c.user.id = :id
             """)
    List<Cart> findAllByUserId(int id);

    void deleteAllByUserId(int id);
}
