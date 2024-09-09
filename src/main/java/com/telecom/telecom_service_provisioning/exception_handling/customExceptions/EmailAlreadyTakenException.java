package com.telecom.telecom_service_provisioning.exception_handling.customExceptions;

public class EmailAlreadyTakenException extends Exception {
    
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
