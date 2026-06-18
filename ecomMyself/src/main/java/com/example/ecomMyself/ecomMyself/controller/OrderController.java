package com.example.ecomMyself.ecomMyself.controller;

import com.example.ecomMyself.ecomMyself.Coupons.Service.CouponService;
import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.DTO.Cart_response;
import com.example.ecomMyself.ecomMyself.model.DTO.Order_item_request;
import com.example.ecomMyself.ecomMyself.model.DTO.Order_request;
import com.example.ecomMyself.ecomMyself.model.DTO.Order_response;
import com.example.ecomMyself.ecomMyself.service.OrderSummaryService;
import com.example.ecomMyself.ecomMyself.service.Order_service;
import com.example.ecomMyself.ecomMyself.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
public class OrderController {
    @Autowired
    private Order_service order_service;
    @Autowired
    private CouponService couponService;
    @Autowired
    private OrderSummaryService orderSummaryService;
    @GetMapping("MyOrders")
    public ResponseEntity<?> getOrder(@AuthenticationPrincipal UserPrincipal principal)
    {
        List<Order_response> orders= order_service.MyOrders(principal);
        if (orders.isEmpty()) {
            return ResponseEntity.status(404).body("No Orders placed");
        }
        return ResponseEntity.ok(orders);
    }
    @GetMapping("MyOrders/")
    public Order_response myOrderId(@RequestParam int id)
    {
        return order_service.myOrderId(id);
    }
//    @PostMapping("AddToCart")
//    public String AddToCart(@RequestBody Order_item_request orderItemRequest)
//    {
//        order_service.AddToCart(orderItemRequest);
//        return "Added to cart";
//    }
    @PostMapping("AddToCart")
    public String AddToCart(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Order_item_request orderItemRequest)
    {
        order_service.AddToCart(principal,orderItemRequest);
        return "Added to cart";
    }
//    @PostMapping("RemoveFromCart")
//    public String RemoveFromCart(@RequestBody Order_item_request orderItemRequest)
//    {
//        order_service.RemoveFromCart(orderItemRequest);
//        return "Removed From cart";
//    }
    @PostMapping("RemoveFromCart")
    public String RemoveFromCart(@AuthenticationPrincipal UserPrincipal principal,@RequestBody Order_item_request orderItemRequest)
    {
        order_service.RemoveFromCart(principal,orderItemRequest);
        return "Removed From cart";
    }
    @GetMapping("Cart")
    public ResponseEntity<?> cart(@AuthenticationPrincipal UserPrincipal principal)
    {
        Cart_response list[]= order_service.cart(principal.getUser().getId());
        return ResponseEntity.ok(list);
    }
    @PostMapping("ApplyCoupon")
    public ResponseEntity<?> applyCoupon(@AuthenticationPrincipal UserPrincipal principal,@RequestBody long couponId)
    {
        try {
            return ResponseEntity.ok(couponService.apply(principal.getUser(),couponId));
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("Coupons")
    public ResponseEntity<?> getCoupons(@AuthenticationPrincipal UserPrincipal principal)
    {
        try {
            return ResponseEntity.ok(couponService.getAllCoupons(principal.getUser()));
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("OrderSummary")
    public ResponseEntity<?> OrderSummary(@AuthenticationPrincipal UserPrincipal principal)
    {
        try {
            return ResponseEntity.ok(orderSummaryService.getOrderSummary(principal.getUser()));
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
