package com.hieunt.ebookapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Service
public class SimpleMailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public static final String MAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public void sendMailMessage(String receiver, String mailSubject, String content){
        Pattern mailPattern = Pattern.compile(MAIL_PATTERN);
        receiver = receiver.trim();
        receiver = receiver.toLowerCase();
        if (!StringUtils.hasText(receiver) || !(mailPattern.matcher(receiver).matches())) {
           throw new MailPreparationException("Email không hợp lệ" +receiver);
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("outlook_9da849b093ad3754@outlook.com");
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setSubject(mailSubject);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }
}
