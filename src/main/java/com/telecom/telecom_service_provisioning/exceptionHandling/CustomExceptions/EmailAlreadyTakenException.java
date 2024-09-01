package com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions;

public class EmailAlreadyTakenException extends Exception {
    
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
