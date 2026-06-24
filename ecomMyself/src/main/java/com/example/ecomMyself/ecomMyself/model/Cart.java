package com.example.ecomMyself.ecomMyself.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private Users user;
    @ManyToOne
    @JoinColumn
    private Product product;
    private String color;
    private int size;
    private int quantity;
    private boolean isStockAvailable;

    public Cart() {
        quantity=0;
    }

    public Cart( Users user, Product product, String color, int size, int quantity,boolean isStockAvailable) {
        this.user = user;
        this.product = product;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.isStockAvailable=isStockAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product= product;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStockAvailable() {
        return isStockAvailable;
    }

    public void setStockAvailable(boolean stockAvailable) {
        isStockAvailable = stockAvailable;
    }
}
