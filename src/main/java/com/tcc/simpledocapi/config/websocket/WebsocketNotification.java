package com.tcc.simpledocapi.config.websocket;

import lombok.Data;

@Data
public class WebsocketNotification {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private String status;
}

