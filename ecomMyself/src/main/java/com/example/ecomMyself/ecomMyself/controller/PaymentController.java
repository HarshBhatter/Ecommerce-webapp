package com.example.ecomMyself.ecomMyself.controller;

import com.example.ecomMyself.ecomMyself.model.DTO.RazorPayDetail;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import com.example.ecomMyself.ecomMyself.service.Order_service;
import com.example.ecomMyself.ecomMyself.service.PaymentService;
import com.example.ecomMyself.ecomMyself.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@CrossOrigin
public class PaymentController {
    @Autowired
    private User_Repo user_repo;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private Order_service order_service;
    @PostMapping("razorpay/payment")
    public RazorPayDetail placeOrder(@AuthenticationPrincipal UserPrincipal principal)
    {
        try{
            return paymentService.placeOrder(principal);
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("razorpay/confirm")
    public String confirmOrder(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserPrincipal principal)
    {
        System.out.println("entering confirmOrder..");
        String razorpayOrderId = body.get("razorpayOrderId");
        String razorpayPaymentId = body.get("razorpayPaymentId");
        String razorpaySignature = body.get("razorpaySignature");
        boolean valid=paymentService.verifySignature(razorpayOrderId,razorpayPaymentId,razorpaySignature);
//        System.out.println(razorpaySignature+" "+valid);

        if(!valid)
            return "FAILED TO PLACE ORDER";

        Users user=principal.getUser();
        user.setCartValue(BigDecimal.ZERO);
        user_repo.save(user);

        order_service.placeOrder(principal,razorpayPaymentId);
        return "ORDER PLACED!";
    }
}
