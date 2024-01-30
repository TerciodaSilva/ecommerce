package com.product.notification.services;

import com.product.notification.dtos.EmailDTO;
import com.product.notification.entities.Email;
import com.product.notification.repositories.EmailRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;


    public void sendEmail(EmailDTO emailDto) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailDto.getFromEmail());
            message.setTo(emailDto.getTo());
            message.setReplyTo(emailDto.getReplyTo());
            message.setSubject(emailDto.getSubject());
            message.setText(emailDto.getBody());

            Email emailModel = new Email();
            BeanUtils.copyProperties(emailDto, emailModel);

            emailRepository.save(emailModel);
            emailSender.send(message);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }
}