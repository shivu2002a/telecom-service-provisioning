package com.telecom.telecom_service_provisioning.service.implementations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;
import com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.repository.InternetServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.InternetServiceRepository;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;

@Service
public class InternetServiceManager {

    @Autowired
    private AuthenticationServiceImpl authService;

    @Autowired
    private InternetServiceRepository internetServiceRepo;

    @Autowired
    private PendingRequestRepository pendingRequestRepo;

    @Autowired
    private InternetServiceAvailedRepository internetServiceAvailedRepo;

    public List<InternetService> getAllInternetService(){
        return internetServiceRepo.findByActiveTrue();
    }

    public InternetService getInternetServiceDetails(Integer id) throws ResourceNotFoundException{
        return internetServiceRepo
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PendingRequest with id: " + id + " doesn't exists"));
    }

    public boolean subscribeToService(Integer serviceId) throws ResourceNotFoundException {
        InternetService service = internetServiceRepo
                                    .findById(serviceId)
                                    .orElseThrow(() -> new ResourceNotFoundException("PendingRequest with id: " + serviceId + " doesn't exists"));
        if (service.getCriteria() == null || service.getCriteria().isEmpty()) {
            // Direct subscription
            Integer userId = authService.getCurrentUserDetails().getUserId();
            availInternetService(userId, serviceId);
            return true;
        } else {
            // Create pending request
            createPendingRequest(serviceId);
            return false;
        }
    }

    public void createPendingRequest(Integer serviceId) {
        PendingRequest request = new PendingRequest();
        User currentUser = authService.getCurrentUserDetails();
        request.setUserId(currentUser.getUserId());
        request.setServiceId(serviceId);
        request.setServiceType(PendingRequestServiceType.INTERNET_SERVICE);
        request.setRequestStatus(PendingRequestStatus.REQUESTED);
        request.setRemarks("Awaiting approval based on criteria: " + internetServiceRepo.findById(serviceId).get().getCriteria());
        request.setActive(true);
        pendingRequestRepo.save(request);
    }

    public void availInternetService(Integer userId, Integer serviceId) {
        InternetServiceAvailed availed = new InternetServiceAvailed();
        availed.setUserId(userId);
        availed.setServiceId(serviceId);
        availed.setStartDate(LocalDate.now());
        availed.setEndDate(LocalDate.now().plusMonths(1));
        availed.setUser(authService.getUserDetailsByUserId(userId));
        availed.setActive(true);
        internetServiceAvailedRepo.save(availed);
    }

    public List<InternetService> getInternetServicesForUpgradeDowngrade(String serviceName, String serviceType) {
        return internetServiceRepo.findByActiveTrueAndServiceNameAndServiceTypeNot(serviceName,serviceType);
    }
}
