package com.tcc.simpledocapi.service.email;

import java.util.Map;

public interface EmailService {

    void sendSimpleMail(EmailDetails details);

    void sendDocumentInvitationEmail(EmailDetails details, String idsPath);

    String sendMailWithAttachment(EmailDetails details);


    void sendEmailWithTemplate(String recipientEmail, String subject, Map<String, Object> templateModel);



}
