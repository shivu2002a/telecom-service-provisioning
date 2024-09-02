package com.telecom.telecom_service_provisioning.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;
import com.telecom.telecom_service_provisioning.service.Interfaces.PendingRequestService;

@Service
public class PendingRequestServiceImpl implements PendingRequestService {

    @Autowired
    private PendingRequestRepository pendingRequestsRepo;

    public void createPendingRequest(PendingRequest pendingRequest) {
        pendingRequestsRepo.save(pendingRequest);
    }

    public List<PendingRequest> getAllPendingRequest() {
        return pendingRequestsRepo.findAll();
    }

    public PendingRequest updatePendingRequest(PendingRequest updatedpendingRequest) throws ResourceNotFoundException {
        PendingRequest existingPendingRequest = pendingRequestsRepo
                                        .findById(updatedpendingRequest.getRequestId())
                                        .orElseThrow(() -> new ResourceNotFoundException("PendingRequest with id " + updatedpendingRequest.getRequestId() + "doesn't exists"));
        existingPendingRequest.setActive(false);
        existingPendingRequest.setRequestStatus(updatedpendingRequest.getRequestStatus());
        existingPendingRequest.setRemarks(updatedpendingRequest.getRemarks());
        return pendingRequestsRepo.save(existingPendingRequest);
    }    

}

