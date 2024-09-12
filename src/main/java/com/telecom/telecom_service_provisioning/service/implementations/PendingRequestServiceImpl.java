package com.telecom.telecom_service_provisioning.service.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;
import com.telecom.telecom_service_provisioning.service.Interfaces.PendingRequestServiceInterface;

@Service
public class PendingRequestServiceImpl implements PendingRequestServiceInterface {

    @Autowired
    private PendingRequestRepository pendingRequestsRepo;

    @Autowired
    private InternetServiceManager internetServiceManager;

    @Autowired
    private TvServiceManager tvServiceManager;

    public void createPendingRequest(PendingRequest pendingRequest) {
        pendingRequestsRepo.save(pendingRequest);
    }

    public List<PendingRequest> getAllPendingRequest() {
        return pendingRequestsRepo.findAll();
    }

    public PendingRequest updatePendingRequest(PendingRequest updatedpendingRequest) throws Exception {
        PendingRequest existingPendingRequest = pendingRequestsRepo
                                        .findById(updatedpendingRequest.getRequestId())
                                        .orElseThrow(() -> new ResourceNotFoundException("PendingRequest with id " + updatedpendingRequest.getRequestId() + "doesn't exists"));
        existingPendingRequest.setActive(false);
        existingPendingRequest.setRequestStatus(updatedpendingRequest.getRequestStatus());
        existingPendingRequest.setRemarks(updatedpendingRequest.getRemarks());
        pendingRequestsRepo.save(existingPendingRequest);
        if(updatedpendingRequest.getRequestStatus().equals(PendingRequestStatus.APPROVED)) {
            if(updatedpendingRequest.getServiceType().equals(PendingRequestServiceType.INTERNET_SERVICE)) {
                internetServiceManager.availInternetService(updatedpendingRequest.getUserId(), updatedpendingRequest.getServiceId());
            }else {
                tvServiceManager.availTvService(updatedpendingRequest.getUserId(), updatedpendingRequest.getServiceId());
            }
        }

        return existingPendingRequest;
    }    

}

