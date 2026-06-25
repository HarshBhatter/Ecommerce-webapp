package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.DTO.RazorPayDetail;
import com.example.ecomMyself.ecomMyself.Notification.Service.NotificationService;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
public abstract class PaymentService {
    @Autowired
    private User_Repo user_repo;
    @Autowired
    private Order_service order_service;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private InventoryReservationService inventoryReservationService;
    public void beforePayment(Users user){
        inventoryReservationService.processingPayment(user);
    }
    @Transactional
    public abstract RazorPayDetail placeOrder(Users user,BigDecimal amtToPay) throws RazorpayException;

    public boolean verifySignature(String orderId, String paymentId, String signature,String apiSecret) {

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        try {
            return Utils.verifyPaymentSignature(options, apiSecret);
        }catch (Exception e)
        {
            return false;
        }
    }
    @Transactional
    public abstract String confirmOrder(Map<String, String> body,Users user);
    public void afterPayment(Users user,String paymentId)
    {
        inventoryReservationService.orderPlaced(user);
        user.setCartValue(BigDecimal.ZERO);
        user_repo.save(user);

        order_service.placeOrder(user,paymentId);
//        try {
//            notificationService.notify(NotificationCategory.ORDER_PLACED, user);
//        }catch (Exception e)
//        {
//            System.out.println("Order placed for "+user.getUsername()+" but there was an error sending the notifincation");
//        }
    }

    public void paymentFailed(Users user) {
        inventoryReservationService.paymentFailed(user);
    }
}
