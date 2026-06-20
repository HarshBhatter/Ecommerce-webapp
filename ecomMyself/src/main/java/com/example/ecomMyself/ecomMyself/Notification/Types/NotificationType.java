package com.example.ecomMyself.ecomMyself.Notification.Types;

import com.example.ecomMyself.ecomMyself.Notification.Message;
import com.example.ecomMyself.ecomMyself.model.OrderSummary;
import com.example.ecomMyself.ecomMyself.model.Users;

public interface NotificationType {
    void send(Message message, Users user);
}
