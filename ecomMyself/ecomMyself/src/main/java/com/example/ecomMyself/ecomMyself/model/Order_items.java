package com.example.ecomMyself.ecomMyself.model;

import jakarta.persistence.*;

import java.util.Optional;
@Entity
public class Order_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Product product;
    private String color;
    private int size;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    public Order_items() {
    }

    public Order_items(int id, Product product, String color, int size, Orders orders,int quantity) {
        this.id = id;
        this.product = product;
        this.color = color;
        this.size = size;
        this.orders = orders;
        this.quantity=quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
