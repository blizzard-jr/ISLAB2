package org.example.is_lab1.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record SuccessResponse<T>(
        String message,
        T data,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp) {
    
    public static <T> SuccessResponse<T> of(String message, T data) {
        return new SuccessResponse<>(message, data, LocalDateTime.now());
    }
    
    public static SuccessResponse<Void> of(String message) {
        return new SuccessResponse<>(message, null, LocalDateTime.now());
    }
}

