package com.example.ecomMyself.ecomMyself.model;

import com.example.ecomMyself.ecomMyself.Enums.ReservationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Inventory_reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private Users user;
    @ManyToOne
    @JoinColumn
    private Product_size product_size;
    private int quantity;
    private LocalDateTime startTime;
    private LocalDateTime expireTime;
    private ReservationStatus status;
    @Version
    private Long version;

    public Inventory_reservation() {
    }

    public Inventory_reservation( Users user, Product_size product_size, int quantity, LocalDateTime startTime, LocalDateTime expireTime, ReservationStatus status) {
        this.user = user;
        this.product_size = product_size;
        this.quantity = quantity;
        this.startTime = startTime;
        this.expireTime = expireTime;
        this.status = status;
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

    public Product_size getProduct_size() {
        return product_size;
    }

    public void setProduct_size(Product_size product_size) {
        this.product_size = product_size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
