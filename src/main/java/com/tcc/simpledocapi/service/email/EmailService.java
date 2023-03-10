package com.tcc.simpledocapi.service.email;

public interface EmailService {

    void sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);



}
