package com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions;

public class ServiceAlreadyAvailedException extends Exception {
    
    public ServiceAlreadyAvailedException(String message) {
        super(message);
    }
}
