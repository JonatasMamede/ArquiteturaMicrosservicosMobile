package com.example.pagamento.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;

@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public String constructOrderContent(String username){
        return MessageFormat.format("<html><body><h1>Olá {0}</h1><p>, sua comprar foi realizada com sucesso!</p></body></html>", username);
    }

    public void sendEmail(String content, String email, String subject){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");

            helper.setFrom(this.mailFrom);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);

            this.mailSender.send(message);

        }catch (MessagingException e){
            System.out.println(e.getMessage());
        }
    }
}