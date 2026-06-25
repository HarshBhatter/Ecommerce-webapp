package com.example.ecomMyself.ecomMyself.controller;
import com.example.ecomMyself.ecomMyself.DTO.RazorPayDetail;
import com.example.ecomMyself.ecomMyself.service.PaymentService;
import com.example.ecomMyself.ecomMyself.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@CrossOrigin
public class PaymentController {
    private PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("payment")
    public RazorPayDetail placeOrder(@AuthenticationPrincipal UserPrincipal principal,@RequestBody BigDecimal amtToPay)
    {
        System.out.println("1st Call to Payment backend");
        try{
            return paymentService.placeOrder(principal.getUser(),amtToPay);
        }catch (Exception e)
        {
            System.out.println("Problem in 1st Call to Payment backend :"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @PostMapping("paymentConfirm")
    public String confirmOrder(@RequestBody Map<String, String> body, @AuthenticationPrincipal UserPrincipal principal)
    {
        System.out.println("Confirm Call to Payment backend");
        try{
            return paymentService.confirmOrder(body,principal.getUser());
        }
        catch (Exception e)
        {
            System.out.println("Problem in Confirm Call to Payment backend +"+e.getMessage() );
            throw new RuntimeException(e);
        }
    }
    @PostMapping("paymentFailed")
    public void paymentFailed(@AuthenticationPrincipal UserPrincipal principal)
    {
        System.out.println("Failed Call to Payment backend");
        try {
            paymentService.paymentFailed(principal.getUser());
        }
        catch (Exception e)
        {
            System.out.println("Problem in Failed Call to Payment backend : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
