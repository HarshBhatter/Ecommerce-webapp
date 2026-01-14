package com.example.ecomMyself.ecomMyself.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String orderId;
    private int userId;
    private String status;
    private LocalDate orderDate;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<Order_items> orderItems;
    private BigDecimal total;

    public Orders() {
    }

    public Orders(Long id, String orderId,int userId, String status, LocalDate orderDate, List<Order_items> orderItems,BigDecimal total) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.total=total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public List<Order_items> getOrderItems() {
        return orderItems;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderItems(List<Order_items> orderItems) {
        this.orderItems = orderItems;
    }
}
