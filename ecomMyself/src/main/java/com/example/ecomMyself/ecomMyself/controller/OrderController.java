package com.example.ecomMyself.ecomMyself.controller;

import com.example.ecomMyself.ecomMyself.Coupons.Service.CouponService;
//import com.example.ecomMyself.ecomMyself.DTO.AddressEmail;
import com.example.ecomMyself.ecomMyself.DTO.Cart_response;
import com.example.ecomMyself.ecomMyself.DTO.Order_item_request;
import com.example.ecomMyself.ecomMyself.DTO.Order_response;
import com.example.ecomMyself.ecomMyself.Embedable.Address;
import com.example.ecomMyself.ecomMyself.DTO.*;
import com.example.ecomMyself.ecomMyself.model.OrderSummary;
import com.example.ecomMyself.ecomMyself.service.OrderSummaryService;
import com.example.ecomMyself.ecomMyself.service.Order_service;
import com.example.ecomMyself.ecomMyself.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            List<Order_response> orders = order_service.MyOrders(principal.getUser());
            if (orders.isEmpty()) {
                return ResponseEntity.status(404).body("No Orders placed");
            }
            return ResponseEntity.ok(orders);
        }catch(Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @GetMapping("MyOrders/")
    public ResponseEntity<?> myOrderId(@RequestParam int id)
    {
        try {
            return ResponseEntity.ok(order_service.myOrderId(id));
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("AddToCart")
    public String AddToCart(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Order_item_request orderItemRequest)
    {
        order_service.AddToCart(principal.getUser(),orderItemRequest);
        return "Added to cart";
    }
//    @PostMapping("RemoveFromCart")
//    public String RemoveFromCart(@RequestBody Order_item_request orderItemRequest)
//    {
//        order_service.RemoveFromCart(orderItemRequest);
//        return "Removed From cart";
//    }
    @PostMapping("RemoveFromCart")
    public ResponseEntity<?> RemoveFromCart(@AuthenticationPrincipal UserPrincipal principal,@RequestBody Order_item_request orderItemRequest)
    {
        try {
            order_service.RemoveFromCart(principal.getUser(), orderItemRequest);
            return ResponseEntity.ok("Removed From cart");
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @GetMapping("Cart")
    public ResponseEntity<?> cart(@AuthenticationPrincipal UserPrincipal principal)
    {
        Cart_response list[]= order_service.cart(principal.getUser().getId());
        return ResponseEntity.ok(list);
    }
    @PostMapping("ApplyCoupon")
    public ResponseEntity<?> applyCoupon(@AuthenticationPrincipal UserPrincipal principal,@RequestBody String couponCode)
    {
        try {
            return ResponseEntity.ok(couponService.apply(principal.getUser(),couponCode));
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("RemoveCoupon")
    public ResponseEntity<?> removeCoupon(@AuthenticationPrincipal UserPrincipal principal)
    {
        try {
            return ResponseEntity.ok(couponService.remove(principal.getUser()));
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
        System.out.println("getting Order Summary..");
        try {
            OrderSummary orderSummary=orderSummaryService.getOrderSummary(principal.getUser());
            String couponCode=orderSummary.getCoupon()==null?"":orderSummary.getCoupon().getCode();
            OrderSummary_response orderSummaryResponse=new OrderSummary_response(orderSummary.getId(),couponCode,orderSummary.getAddress(),orderSummary.getTotal(),orderSummary.getDiscount(),orderSummary.getDiscountedTotal(),orderSummary.getExpiry());
            return ResponseEntity.ok(orderSummaryResponse);
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("saveAddress")
    public ResponseEntity<?> saveAddress(@AuthenticationPrincipal UserPrincipal principal,@RequestBody Address address)
    {
        try {
            orderSummaryService.saveAddress(principal.getUser(), address);
            return ResponseEntity.ok("saved Address and Email");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
