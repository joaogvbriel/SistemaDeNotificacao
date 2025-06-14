package com.joaogvbriel.sdn.service;

import com.joaogvbriel.sdn.dto.NotificationsDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${AWS_EMAIL}")
    private String AwsEmail;

    public void sendEmail(NotificationsDTO notifications) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);

        mimeMessageHelper.setFrom(AwsEmail);
        mimeMessageHelper.setTo(notifications.getRecipient());
        mimeMessageHelper.setSubject("Notificação");
        mimeMessageHelper.setText(notifications.getMessage());

        javaMailSender.send(message);
    }

}
