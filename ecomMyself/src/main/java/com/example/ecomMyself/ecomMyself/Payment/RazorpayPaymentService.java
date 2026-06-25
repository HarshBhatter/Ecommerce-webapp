package com.example.ecomMyself.ecomMyself.Payment;

import com.example.ecomMyself.ecomMyself.DTO.RazorPayDetail;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
@Service("razorpay")
public class RazorpayPaymentService extends PaymentService {
    @Value("${razorpay.key.id}")
    private String apikey;
    @Value("${razorpay.key.secret}")
    private String apiSecret;
    @Override
    public RazorPayDetail placeOrder(Users user, BigDecimal amtToPay) throws RazorpayException {
        beforePayment(user);

        RazorpayClient razorpay = new RazorpayClient(apikey, apiSecret);
        JSONObject orderRequest = new JSONObject();
        BigDecimal amount=amtToPay.multiply(BigDecimal.valueOf(100));

        orderRequest.put("amount",amount);
        orderRequest.put("currency","INR");
        orderRequest.put("receipt", user.getUsername());

        Order order = razorpay.orders.create(orderRequest);

        return new RazorPayDetail(apikey,order.get("id"),amount);
    }

    @Override
    public String confirmOrder(Map<String, String> body, Users user)
    {
        System.out.println("entering confirmOrder..");
        String razorpayOrderId = body.get("razorpayOrderId");
        String razorpayPaymentId = body.get("razorpayPaymentId");
        String razorpaySignature = body.get("razorpaySignature");
        boolean valid=verifySignature(razorpayOrderId,razorpayPaymentId,razorpaySignature,apiSecret);
//        System.out.println(razorpaySignature+" "+valid);

        if(!valid) {
            throw new RuntimeException("FAILED TO PLACE ORDER AS SIGNATURE NOT VALID");
        }

        afterPayment(user,razorpayPaymentId);
        return "ORDER PLACED!";
    }
}
