package com.example.ecomMyself.ecomMyself.Notification;

import com.example.ecomMyself.ecomMyself.Notification.Enum.NotificationCategory;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.service.Order_service;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageGenerator {
    @Autowired
    private Order_service order_service;
    public Message getMessage(NotificationCategory notificationCategory, Users user)
    {
        if(notificationCategory==NotificationCategory.NEW_USER)
            return new Message("Welcome!!!","Hi "+ user.getUsername()+",\n" +
                    "\n" +
                    "Welcome to our store! " +
                    "\n" +
                    "We're excited to have you with us. Your account has been created successfully, and you can now explore our collection, add items to your cart, and place orders with ease.\n" +
                    "\n" +
                    "Thank you for choosing us. We hope you have a great shopping experience!\n" +
                    "\n" +
                    "Happy Shopping!");

        if(notificationCategory==NotificationCategory.ORDER_PLACED)
            return new Message("Order Placed!!","Yayyy!! \n Hi "+ user.getUsername()+",\n" +
                    "Your order (Order Id:"+order_service.getLatestOrderId(user)+") has been placed successfully and is now being processed. We'll notify you once it has been shipped.\n" +
                    "\n" +
                    "We appreciate your trust in us and hope you enjoy your purchase!\n" +
                    "\n" +
                    "Happy Shopping!");

        return new Message("Forgott to add subject","OOPs forgot to add the message for this category");
    }
}
