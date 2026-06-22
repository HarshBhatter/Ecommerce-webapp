package com.example.ecomMyself.ecomMyself.controller;

import com.example.ecomMyself.ecomMyself.Notification.Enum.NotificationCategory;
import com.example.ecomMyself.ecomMyself.Notification.Service.NotificationService;
import com.example.ecomMyself.ecomMyself.DTO.RazorPayDetail;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import com.example.ecomMyself.ecomMyself.service.OrderSummaryService;
import com.example.ecomMyself.ecomMyself.service.Order_service;
import com.example.ecomMyself.ecomMyself.service.PaymentService;
import com.example.ecomMyself.ecomMyself.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private OrderSummaryService orderSummaryService;
    @Autowired
    private NotificationService notificationService;
    @PostMapping("razorpay/payment")
    public RazorPayDetail placeOrder(@AuthenticationPrincipal UserPrincipal principal,@RequestBody BigDecimal amtToPay)
    {
        try{
            return paymentService.placeOrder(principal,amtToPay);
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    @Transactional
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
        orderSummaryService.orderPlaced(user);
        System.out.println("pass1");
        user.setCartValue(BigDecimal.ZERO);
        user_repo.save(user);
        System.out.println("pass2");
        order_service.placeOrder(principal,razorpayPaymentId);
        System.out.println("pass3");
        notificationService.notify(NotificationCategory.ORDER_PLACED,principal.getUser());
        System.out.println("pass4");


        return "ORDER PLACED!";
    }
}
