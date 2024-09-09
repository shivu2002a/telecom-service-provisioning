package com.telecom.telecom_service_provisioning.exception_handling;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class ErrorResponse {
    
    private String message;
    private String description;
    private LocalDateTime timestamp;

}
