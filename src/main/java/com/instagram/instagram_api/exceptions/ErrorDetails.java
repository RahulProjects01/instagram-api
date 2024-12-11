package com.instagram.instagram_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {

    private String message;
    private String details;
    private LocalDateTime timestamp;



    public ErrorDetails(String message, String details, LocalDateTime timestamp) {
        super();
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }
}
