package com.telecom.telecom_service_provisioning.service.implementations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;
import com.telecom.telecom_service_provisioning.dto.ModifySubscription;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.MaxServicesAlreadyAvailedException;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceRepository;

@Service
public class TvServiceManager {
    
    @Autowired
    private AuthenticationServiceImpl authService;

    @Autowired
    private TvServiceRepository tvServiceRepo;

    @Autowired
    private PendingRequestRepository pendingRequestRepo;

    @Autowired
    private TvServiceAvailedRepository tvServiceAvailedRepo;

    public List<TvService> getAllTvService(){
        return tvServiceRepo.findByActiveTrue();
    }

    public TvService getTvServiceDetails(Integer id){
        return tvServiceRepo.findById(id).get();
    }

    public boolean subscribeToTvService(Integer serviceId) throws Exception {
        TvService service = tvServiceRepo
                            .findById(serviceId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tv Service with id: " + serviceId + "doesn't exists !!"));
        if (service.getCriteria() == null || service.getCriteria().isEmpty()) {
            // Direct subscription
            Integer userId = authService.getCurrentUserDetails().getUserId();
            availTvService(userId, serviceId);
            return true;
        } else {
            // Create pending request
            createPendingRequest(serviceId);
            return false;
        }
    }

    private void createPendingRequest(Integer serviceId) {
        PendingRequest request = new PendingRequest();
        User currentUser = authService.getCurrentUserDetails();
        request.setUserId(currentUser.getUserId());
        request.setServiceId(serviceId);
        request.setServiceType(PendingRequestServiceType.TV_SERVICE);
        request.setRequestStatus(PendingRequestStatus.REQUESTED);
        request.setRemarks("Awaiting approval based on criteria: " + tvServiceRepo.findById(serviceId).get().getCriteria());
        request.setActive(true);
        pendingRequestRepo.save(request);
    }

    public void availTvService(Integer userId, Integer serviceId) throws Exception {
        //Check if he has subscribed to one, if yes, keep it in q
        List<TvServiceAvailed> currentservices = tvServiceAvailedRepo.findByUserIdAndActiveTrue(userId);
        int availedServices = currentservices.size(); 
        int pendingRequests = pendingRequestRepo.findByUserIdAndServiceTypeAndActiveTrue(userId, PendingRequestServiceType.TV_SERVICE).size();
        if( pendingRequests + availedServices >= 2) {
            throw new MaxServicesAlreadyAvailedException("Already availed 2 Tv services");
        }
        TvService toSubscribeService = tvServiceRepo.findById(serviceId).get();
        TvServiceAvailed availed = new TvServiceAvailed();
        availed.setUserId(userId);
        availed.setServiceId(serviceId);
        availed.setActive(true);
        if(availedServices == 0) {
            availed.setStartDate(LocalDate.now());
            availed.setEndDate(LocalDate.now().plusDays(toSubscribeService.getValidity()));
        } else {
            availed.setStartDate(currentservices.get(0).getEndDate());
            availed.setEndDate(currentservices.get(0).getEndDate().plusDays(toSubscribeService.getValidity()));
        }
        tvServiceAvailedRepo.save(availed);
    }

    public List<TvService> getTvServicesForUpgradeDowngrade(String serviceName, String serviceType) {
        return tvServiceRepo.findByActiveTrueAndServiceNameAndServiceTypeNot(serviceName,serviceType);
    }

    public TvServiceAvailed modifySubscription(ModifySubscription modifySubscription) {
        TvServiceAvailed updatedAvail = new TvServiceAvailed();
        updatedAvail.setUserId(authService.getCurrentUserDetails().getUserId());
        updatedAvail.setServiceId(modifySubscription.getNewServiceId());
        updatedAvail.setStartDate(modifySubscription.getStartDate());
        updatedAvail.setActive(true);
        updatedAvail.setEndDate(modifySubscription.getEndDate());
        updatedAvail.setTvService(tvServiceRepo.findById(modifySubscription.getNewServiceId()).get());
        return tvServiceAvailedRepo.save(updatedAvail);
    }
}
