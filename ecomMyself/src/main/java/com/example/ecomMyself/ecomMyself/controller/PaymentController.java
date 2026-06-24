package com.example.ecomMyself.ecomMyself.controller;
import com.example.ecomMyself.ecomMyself.DTO.RazorPayDetail;
import com.example.ecomMyself.ecomMyself.service.PaymentService;
import com.example.ecomMyself.ecomMyself.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@CrossOrigin
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("razorpay/payment")
    public RazorPayDetail placeOrder(@AuthenticationPrincipal UserPrincipal principal,@RequestBody BigDecimal amtToPay)
    {
        try{
            return paymentService.placeOrder(principal.getUser(),amtToPay);
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("razorpay/confirm")
    public String confirmOrder(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserPrincipal principal)
    {
        try{
            return paymentService.confirmOrder(body,principal.getUser());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("paymentFailed")
    public void paymentFailed(@AuthenticationPrincipal UserPrincipal principal)
    {
        paymentService.paymentFailed(principal.getUser());
    }
}
