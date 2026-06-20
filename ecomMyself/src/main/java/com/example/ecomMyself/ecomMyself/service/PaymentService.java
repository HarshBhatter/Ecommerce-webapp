package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.DTO.RazorPayDetail;
import com.example.ecomMyself.ecomMyself.repository.Cart_repo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {
    @Autowired
    private Cart_repo cartRepo;
    @Value("${razorpay.key.id}")
    private String apikey;
    @Value("${razorpay.key.secret}")
    private String apiSecret;
    public RazorPayDetail placeOrder(UserPrincipal principal) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(apikey, apiSecret);
        JSONObject orderRequest = new JSONObject();
        BigDecimal amount=principal.getCartValue().multiply(BigDecimal.valueOf(100));

        orderRequest.put("amount",amount);
        orderRequest.put("currency","INR");
        orderRequest.put("receipt", principal.getUsername());

        Order order = razorpay.orders.create(orderRequest);
        return new RazorPayDetail(apikey,order.get("id"),amount);
    }

    public boolean verifySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", razorpayOrderId);
        options.put("razorpay_payment_id", razorpayPaymentId);
        options.put("razorpay_signature", razorpaySignature);

        try {
            return Utils.verifyPaymentSignature(options, apiSecret);
        }catch (Exception e)
        {
            return false;
        }
    }
}
