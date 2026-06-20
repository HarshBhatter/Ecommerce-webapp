package com.example.ecomMyself.ecomMyself.model;

import com.example.ecomMyself.ecomMyself.Embedable.Address;
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
    private String couponCodeApplied;
    private BigDecimal discount;
    private BigDecimal discountedTotal;
    private String razorpayPaymentId;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "state", column = @Column(name = "state")),
            @AttributeOverride(name = "pincode", column = @Column(name = "pincode"))
    })
    private Address address;

    public Orders() {
    }

    public Orders(Long id, String orderId,int userId, String status, LocalDate orderDate, List<Order_items> orderItems,BigDecimal total,String razorpayPaymentId,Address address,BigDecimal discount,BigDecimal discountedTotal,String couponCodeApplied) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.razorpayPaymentId=razorpayPaymentId;
        this.address=address;
        this.couponCodeApplied=couponCodeApplied;
        this.total=total;
        this.discount=discount;
        this.discountedTotal=discountedTotal;
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

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCouponCodeApplied() {
        return couponCodeApplied;
    }

    public void setCouponCodeApplied(String couponCodeApplied) {
        this.couponCodeApplied = couponCodeApplied;
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
