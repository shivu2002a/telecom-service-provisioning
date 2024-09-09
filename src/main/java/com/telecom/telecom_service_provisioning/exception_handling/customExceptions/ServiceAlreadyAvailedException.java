package com.telecom.telecom_service_provisioning.exception_handling.customExceptions;

public class ServiceAlreadyAvailedException extends Exception {
    
    public ServiceAlreadyAvailedException(String message) {
        super(message);
    }
}
