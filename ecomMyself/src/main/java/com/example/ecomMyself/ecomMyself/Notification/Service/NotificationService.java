package com.example.ecomMyself.ecomMyself.Notification.Service;

import com.example.ecomMyself.ecomMyself.Notification.Enum.NotificationCategory;
import com.example.ecomMyself.ecomMyself.Notification.Message;
import com.example.ecomMyself.ecomMyself.Notification.MessageGenerator;
import com.example.ecomMyself.ecomMyself.Notification.Types.NotificationType;
import com.example.ecomMyself.ecomMyself.model.OrderSummary;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final List<NotificationType> notificationTypes;
    @Autowired
    private MessageGenerator messageGenerator;

    public NotificationService(List<NotificationType> notificationTypes) {
        this.notificationTypes = notificationTypes;
    }

    public void notify(NotificationCategory notificationCategory, Users user)
    {
        Message message = messageGenerator.getMessage(notificationCategory,user);
        for(NotificationType nt:notificationTypes)
            nt.send(message,user);
    }
}
