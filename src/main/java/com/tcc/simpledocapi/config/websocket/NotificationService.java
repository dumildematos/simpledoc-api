package com.tcc.simpledocapi.config.websocket;

import com.tcc.simpledocapi.dto.NotificationResponseContentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    public void sendGlobalNotification() {
        NotificationResponseContentDTO message = new NotificationResponseContentDTO("Global Notification");
        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    public void sendPrivateNotification(final String userId) {
        NotificationResponseContentDTO message = new NotificationResponseContentDTO("Private Notification");
        messagingTemplate.convertAndSendToUser(userId,"/topic/private-notifications", message);
    }

}
