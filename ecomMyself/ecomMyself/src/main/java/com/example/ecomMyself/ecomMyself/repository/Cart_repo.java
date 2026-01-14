package com.example.ecomMyself.ecomMyself.repository;

import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.DTO.Order_item_request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Cart_repo extends JpaRepository<Cart,Integer> {

    Cart findByUserIdAndProductIdAndColorAndSize(int userId,int productid,String color,int size);

    List<Cart> findAllByUserId(int id);

    void deleteAllByUserId(int id);
}
