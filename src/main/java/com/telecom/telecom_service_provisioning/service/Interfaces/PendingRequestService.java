package com.telecom.telecom_service_provisioning.service.Interfaces;

import com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.PendingRequest;

public interface PendingRequestService {
    
    public void createPendingRequest(PendingRequest pendingRequest);
    
    public PendingRequest updatePendingRequest(PendingRequest updatedPendingRequest) throws ResourceNotFoundException;
}
