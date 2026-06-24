package com.example.ecomMyself.ecomMyself.Notification.Types;

import com.example.ecomMyself.ecomMyself.Notification.Message;
import com.example.ecomMyself.ecomMyself.model.OrderSummary;
//import org.springframework.beans.factory.annotation.Autowired;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotification implements NotificationType {
    private final JavaMailSender mailSender;
    public EmailNotification(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(Message messageBody, Users user) {

//        with java wmail sender:-

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject(messageBody.subject());
        message.setText(messageBody.text());

        mailSender.send(message);
    }
}
