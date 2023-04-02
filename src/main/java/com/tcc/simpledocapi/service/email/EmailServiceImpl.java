package com.tcc.simpledocapi.service.email;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;


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
            //mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            String html = "<html><body>" +
                    "<h3>"+details.getSubject()+"</h3>" +
                    "<p>"+ details.getMsgBody() +"</p>" +
                    "<p><a href=\"" + details.getLink() + "\" style=\"background-color: #4c5fe1; color: white; padding: 10px 16px; text-align: center; text-decoration: none; display: inline-block; border-radius: 5px;\">Click me</a></p>" +
                    "</body></html>";
            // Sending the mail
            mailMessage.setText(html);
            javaMailSender.send(mailMessage);
            // log.info("Mail Sent Successfully...to:   " + details.getRecipient());
            // log.info("... From: " + sender);
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            log.info("Error while Sending Mail...");
            log.info(e.toString());
        }
    }

    public void sendDocumentInvitationEmail(EmailDetails details, String idsPath) {
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            //mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            String html = "<html><style>*{font-family: sans-serif;padding: 5px;margin: 5px;}</style><body>" +
                    "<h3>"+details.getSubject()+"</h3>" +
                    "<p>"+ details.getMsgBody() +"</p>" +
                    "<p>" +
                    "<a href=\"" + details.getLink() + "/accept/" + idsPath +"\" style=\"background-color: #4c5fe1; color: white; padding: 10px 16px; text-align: center; text-decoration: none; display: inline-block; border-radius: 5px;\">Accept</a>" +
                    "<a href=\"" + details.getLink() + "/reject/" + idsPath + "\" style=\"background-color: #cf4749; color: white; padding: 10px 16px; text-align: center; text-decoration: none; display: inline-block; border-radius: 5px;\">Deny</a>"+
                    "</p>" +

                    "</body></html>";
            // Sending the mail
            mailMessage.setText(html);
            javaMailSender.send(mailMessage);
            // log.info("Mail Sent Successfully...to:   " + details.getRecipient());
            // log.info("... From: " + sender);
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

    @Override
    public void sendEmailWithTemplate(String recipientEmail, String subject, Map<String, Object> templateModel) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(subject);
            String content = generateTemplateContent(templateModel);
            messageHelper.setText(content, true);
        };
        javaMailSender.send(messagePreparator);

    }

    private String generateTemplateContent(Map<String, Object> templateModel) throws MessagingException {
        Context context = new Context();
        context.setVariables(templateModel);
        return templateEngine.process("complete-registration", context);
    }
}
