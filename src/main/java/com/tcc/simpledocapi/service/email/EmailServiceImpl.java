package com.tcc.simpledocapi.service.email;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}") private String sender;


    @Override
    @Async
    public void sendSimpleMail(EmailDetails details) {
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            log.info("Mail Sent Successfully...to:   " + details.getRecipient());
            log.info("... From: " + sender);
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            log.info("Error while Sending Mail...");
            log.info(e.toString());
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        return null;
    }
}
