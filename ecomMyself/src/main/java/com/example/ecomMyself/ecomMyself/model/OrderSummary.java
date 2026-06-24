package com.example.ecomMyself.ecomMyself.model;

import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import com.example.ecomMyself.ecomMyself.Embedable.Address;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "state", column = @Column(name = "state")),
            @AttributeOverride(name = "pincode", column = @Column(name = "pincode"))
    })
    private Address address;
    private BigDecimal total;
    private BigDecimal discount;
    private BigDecimal discountedTotal;
    private LocalDateTime expiry;

    public OrderSummary() {
        this.expiry=LocalDateTime.now(ZoneOffset.UTC).plusMinutes(4).plusSeconds(50);
    }

    public OrderSummary(long id, Users user, Coupon coupon, Address address, BigDecimal total, BigDecimal discount, BigDecimal discountedTotal) {
        this.id = id;
        this.user = user;
        this.coupon = coupon;
        this.address=address;
        this.total=total;
        this.discount=discount;
        this.discountedTotal=discountedTotal;
        this.expiry=LocalDateTime.now(ZoneOffset.UTC).plusMinutes(4).plusSeconds(50);
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }
}
