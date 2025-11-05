package org.example.is_lab1.services;

import lombok.RequiredArgsConstructor;
import org.example.is_lab1.models.dto.WebSocketMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyCreated(String entityType, Integer entityId, Object payload) {
        WebSocketMessage message = WebSocketMessage.created(entityType, entityId, payload);
        messagingTemplate.convertAndSend("/topic/entities", message);
    }

    public void notifyUpdated(String entityType, Integer entityId, Object payload) {
        WebSocketMessage message = WebSocketMessage.updated(entityType, entityId, payload);
        messagingTemplate.convertAndSend("/topic/entities", message);
    }

    public void notifyDeleted(String entityType, Integer entityId) {
        WebSocketMessage message = WebSocketMessage.deleted(entityType, entityId);
        messagingTemplate.convertAndSend("/topic/entities", message);
    }

}





