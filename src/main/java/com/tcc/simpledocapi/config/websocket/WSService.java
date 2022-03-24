package com.tcc.simpledocapi.config.websocket;

import com.tcc.simpledocapi.dto.NotificationResponseContentDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    public void notifyFrontend(final String message) {
        NotificationResponseContentDTO response = new NotificationResponseContentDTO(message);
        notificationService.sendGlobalNotification();

        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    public void notifyUser(final String id, final String message) {
        NotificationResponseContentDTO response = new NotificationResponseContentDTO(message);

        notificationService.sendPrivateNotification(id);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);
    }

}
