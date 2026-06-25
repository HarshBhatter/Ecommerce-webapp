package com.example.ecomMyself.ecomMyself.configuration;

import com.example.ecomMyself.ecomMyself.Payment.RazorpayPaymentService;
import com.example.ecomMyself.ecomMyself.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {
    @Bean
    public PaymentService paymentService(RazorpayPaymentService razorpay, @Value("${payment.provider}") String provider) {

        return switch (provider) {
            case "razorpay" -> razorpay;
            default -> throw new IllegalArgumentException("Unknown provider");
        };
    }
}
