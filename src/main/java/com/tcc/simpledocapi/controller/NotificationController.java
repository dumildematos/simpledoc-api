package com.tcc.simpledocapi.controller;

import com.tcc.simpledocapi.config.websocket.WebsocketNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {


    /*@MessageMapping("/private-notification")
    public WebsocketNotification privateNotification (@Payload WebsocketNotification notification) {
        simpMessagingTemplate.convertAndSendToUser(notification.getReceiverName(), "/private", notification);
        return notification;
    }*/

}
