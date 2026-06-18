package com.example.ecomMyself.ecomMyself.Coupons.Model;

import com.example.ecomMyself.ecomMyself.Coupons.Enums.DiscountType;
import com.example.ecomMyself.ecomMyself.Coupons.Enums.CouponCategory;
import com.example.ecomMyself.ecomMyself.model.OrderSummary;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    @Enumerated(EnumType.STRING)
    private CouponCategory couponCategory;
    private double discountValue;
    private Double maxDiscount;
    private BigDecimal minimumCartValue;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private String gender;
    private boolean isActive;
    private HashMap<Long,Integer> usageMap;
    private int limitPerUser;
    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<OrderSummary> orderSummaryList;

    public Coupon(Long id, String code, DiscountType discountType, CouponCategory couponCategory, double discountValue, Double maxDiscount, BigDecimal  minimumCartValue, LocalDate startDate, LocalDate expiryDate, String gender,int limitPerUser) {
        this.id = id;
        this.code = code;
        this.discountType = discountType;
        this.couponCategory = couponCategory;
        this.discountValue = discountValue;
        this.maxDiscount = maxDiscount;
        this.minimumCartValue = minimumCartValue;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.gender = gender;
        this.limitPerUser=limitPerUser;
        usageMap=new HashMap<>();
        isActive=true;
    }

    public Coupon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public CouponCategory getEligibilityCategory() {
        return couponCategory;
    }

    public void setEligibilityCategory(CouponCategory couponCategory) {
        this.couponCategory = couponCategory;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public BigDecimal  getMinimumCartValue() {
        return minimumCartValue;
    }

    public void setMinimumCartValue(BigDecimal  minimumCartValue) {
        this.minimumCartValue = minimumCartValue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public CouponCategory getCouponCategory() {
        return couponCategory;
    }

    public void setCouponCategory(CouponCategory couponCategory) {
        this.couponCategory = couponCategory;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public HashMap<Long, Integer> getUsage() {
        if(usageMap==null)
            usageMap=new HashMap<>();
        return usageMap;
    }

    public void setUsage(HashMap<Long, Integer> usage) {
        this.usageMap = usage;
    }

    public int getLimitPerUser() {
        return limitPerUser;
    }

    public void setLimitPerUser(int limitPerUser) {
        this.limitPerUser = limitPerUser;
    }
}
