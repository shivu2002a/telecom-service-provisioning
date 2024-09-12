package com.telecom.telecom_service_provisioning.service.Interfaces;

import java.util.List;

import com.telecom.telecom_service_provisioning.model.PendingRequest;

public interface PendingRequestServiceInterface {
    
    public void createPendingRequest(PendingRequest pendingRequest);
    
    public PendingRequest updatePendingRequest(PendingRequest updatedPendingRequest) throws Exception;

    public List<PendingRequest> getAllPendingRequest();

}
