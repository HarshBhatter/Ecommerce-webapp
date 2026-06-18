package com.example.ecomMyself.ecomMyself.model;

import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Entity
public class OrderSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Users user;
    @ManyToOne
    @JoinColumn(name="Couponid")
    private Coupon coupon;
    private String street;
    private String city;
    private String state;
    private int pincode;
    private String emailId;
    private BigDecimal total;
    private BigDecimal discount;
    private BigDecimal discountedTotal;

    public OrderSummary() {
    }

    public OrderSummary(long id, Users user, Coupon coupon, String street, String city, String state, int pincode, String emailId,BigDecimal total, BigDecimal discount, BigDecimal discountedTotal) {
        this.id = id;
        this.user = user;
        this.coupon = coupon;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.emailId = emailId;
        this.total=total;
        this.discount=discount;
        this.discountedTotal=discountedTotal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountedTotal() {
        return discountedTotal;
    }

    public void setDiscountedTotal(BigDecimal discountedTotal) {
        this.discountedTotal = discountedTotal;
    }
}
