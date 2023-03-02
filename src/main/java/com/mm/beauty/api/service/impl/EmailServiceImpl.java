package com.mm.beauty.api.service.impl;

import com.mm.beauty.api.entity.EmailModel;
import com.mm.beauty.api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;




@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${sender.mail}")
    private String sender;
    @Override
    public void sendSimpleMail(EmailModel details) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(sender);
        msg.setTo(details.getRecipient());
        msg.setSubject(details.getSubject());
        msg.setText(details.getMsgBody());
        javaMailSender.send(msg);
    }
}
