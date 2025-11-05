package org.example.is_lab1.models.dto;

public record WebSocketMessage(
        String type,
        String entityType,
        Integer entityId,
        Object payload
) {
    public static WebSocketMessage created(String entityType, Integer entityId, Object payload) {
        return new WebSocketMessage("CREATED", entityType, entityId, payload);
    }

    public static WebSocketMessage updated(String entityType, Integer entityId, Object payload) {
        return new WebSocketMessage("UPDATED", entityType, entityId, payload);
    }

    public static WebSocketMessage deleted(String entityType, Integer entityId) {
        return new WebSocketMessage("DELETED", entityType, entityId, null);
    }
}

