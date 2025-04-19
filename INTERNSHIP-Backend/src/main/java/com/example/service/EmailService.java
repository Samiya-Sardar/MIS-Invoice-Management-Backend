package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordEmail(String toEmail, String name, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Forgot Password Assistance");
        message.setText("Hello " + name + ",\n\nYour password is: " + password + "\n\nRegards,\nSupport Team");
        message.setFrom("sardarsamiya@gmail.com"); // same as configured in application.properties

        mailSender.send(message);
    }
    
    
}