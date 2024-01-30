package com.product.notification.consumers;

import com.product.notification.dtos.EmailDTO;
import com.product.notification.dtos.UserCreatedEvent;
import com.product.notification.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    EmailService emailService;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String email;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void onUserCreated(UserCreatedEvent event) {

        String fromName = "E-commerce Microservice";
        String replyTo = email;
        String to = event.getEmail();
        String subject = "Registered in the e-commerce microservice";
        String body = "Congratulations " + event.getUserName() + " your registration in e-commerce microservice was a success";
        String contentType = "Success";

        EmailDTO emailDto = new EmailDTO(email, fromName, replyTo, to, subject, body, contentType);

        emailService.sendEmail(emailDto);
    }
}
