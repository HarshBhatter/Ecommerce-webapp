package com.example.ecomMyself.ecomMyself.Notification.Types;

import com.example.ecomMyself.ecomMyself.Notification.Message;
import com.example.ecomMyself.ecomMyself.Notification.Types.NotificationType;
import com.example.ecomMyself.ecomMyself.model.OrderSummary;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.stereotype.Component;

@Component
public class WhatsappNotification implements NotificationType {
    @Override
    public void send(Message message, Users user) {

    }
}
