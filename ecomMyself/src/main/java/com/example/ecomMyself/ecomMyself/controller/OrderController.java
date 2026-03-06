package com.example.ecomMyself.ecomMyself.controller;

import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.DTO.Cart_response;
import com.example.ecomMyself.ecomMyself.model.DTO.Order_item_request;
import com.example.ecomMyself.ecomMyself.model.DTO.Order_request;
import com.example.ecomMyself.ecomMyself.model.DTO.Order_response;
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
//    @PostMapping("PlaceOrder")
//    public String placeOrder()
//    {
//        order_service.placeOrder();
//        return "Order placed";
//    }

//    @PostMapping("PlaceOrder")
//    public String placeOrder(@AuthenticationPrincipal UserPrincipal principal)
//    {
//        order_service.placeOrder(principal);
//        return "Order placed";
//    }
//    @GetMapping("MyOrders")
//    public List<Order_response> MyOrders()
//    {
//        return order_service.MyOrders();
//    }

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
}
