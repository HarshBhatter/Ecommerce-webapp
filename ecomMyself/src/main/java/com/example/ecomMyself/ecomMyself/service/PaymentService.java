package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.DTO.RazorPayDetail;
import com.example.ecomMyself.ecomMyself.Notification.Enum.NotificationCategory;
import com.example.ecomMyself.ecomMyself.Notification.Service.NotificationService;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.Cart_repo;
import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import com.mysql.cj.log.Log;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    private Cart_repo cartRepo;
    @Value("${razorpay.key.id}")
    private String apikey;
    @Value("${razorpay.key.secret}")
    private String apiSecret;
    @Autowired
    private User_Repo user_repo;
    @Autowired
    private OrderSummaryService orderSummaryService;
    @Autowired
    private Order_service order_service;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private InventoryReservationService inventoryReservationService;
    public RazorPayDetail placeOrder(Users user,BigDecimal amtToPay) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(apikey, apiSecret);
        JSONObject orderRequest = new JSONObject();
        BigDecimal amount=amtToPay.multiply(BigDecimal.valueOf(100));

        orderRequest.put("amount",amount);
        orderRequest.put("currency","INR");
        orderRequest.put("receipt", user.getUsername());

        inventoryReservationService.processingPayment(user);

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
    @Transactional
    public String confirmOrder(Map<String, String> body,Users user)
    {
        System.out.println("entering confirmOrder..");
        String razorpayOrderId = body.get("razorpayOrderId");
        String razorpayPaymentId = body.get("razorpayPaymentId");
        String razorpaySignature = body.get("razorpaySignature");
        boolean valid=verifySignature(razorpayOrderId,razorpayPaymentId,razorpaySignature);
//        System.out.println(razorpaySignature+" "+valid);

        if(!valid) {
            throw new RuntimeException("FAILED TO PLACE ORDER AS SIGNATURE NOT VALID");
        }
        inventoryReservationService.orderPlaced(user);
        user.setCartValue(BigDecimal.ZERO);
        user_repo.save(user);

        order_service.placeOrder(user,razorpayPaymentId);
//        try {
//            notificationService.notify(NotificationCategory.ORDER_PLACED, user);
//        }catch (Exception e)
//        {
//            System.out.println("Order placed for "+user.getUsername()+" but there was an error sending the notifincation");
//        }

        return "ORDER PLACED!";
    }

    public void paymentFailed(Users user) {
        inventoryReservationService.paymentFailed(user);
    }
}
