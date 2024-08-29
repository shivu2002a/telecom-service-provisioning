package com.telecom.telecom_service_provisioning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;

@Service
public class PendingRequestService {

    @Autowired
    private PendingRequestRepository pendingRequestsRepo;

    public List<PendingRequest> getAllPendingRequest() {
        return pendingRequestsRepo.findByActiveTrue();
    }

    public void createPendingRequest(PendingRequest pendingRequest) {
        pendingRequestsRepo.save(pendingRequest);
    }

    public void updatePendingRequest(PendingRequest pendingRequest,String requestStatus,String remarks) {
        pendingRequest.setRequestStatus(requestStatus);
        pendingRequest.setRemarks(remarks);
        pendingRequest.setActive(false);
        pendingRequestsRepo.save(pendingRequest);
    }


}

