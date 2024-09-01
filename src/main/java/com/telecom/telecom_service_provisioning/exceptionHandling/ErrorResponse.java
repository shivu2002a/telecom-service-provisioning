package com.telecom.telecom_service_provisioning.exceptionHandling;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;


@Data
public class ErrorResponse {
    
    private String message;
    private String description;
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;

}
