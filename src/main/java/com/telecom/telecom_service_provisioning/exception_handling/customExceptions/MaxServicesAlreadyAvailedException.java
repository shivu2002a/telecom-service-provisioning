package com.telecom.telecom_service_provisioning.exception_handling.customExceptions;

public class MaxServicesAlreadyAvailedException extends Exception {
    
    public MaxServicesAlreadyAvailedException(String message) {
        super(message);
    }
}
