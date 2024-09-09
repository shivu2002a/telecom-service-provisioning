package com.telecom.telecom_service_provisioning.service.implementations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.compositekey_models.InternetServicesAvailedId;
import com.telecom.telecom_service_provisioning.repository.InternetServiceAvailedRepository;

@Service
public class AvailedInternetServiceManager {

    @Autowired
    private AuthenticationServiceImpl authService;

    @Autowired
    private InternetServiceAvailedRepository internetServiceAvailedRepo;

    public java.util.List<InternetServiceAvailed> getActiveSubscribedServices(Integer userId) {
        return internetServiceAvailedRepo.findByUserIdAndActiveTrue(userId);
    }

    public void deactivateService(Integer availedServiceId, LocalDate startDate) throws Exception {
        Integer userId = authService.getCurrentUserDetails().getUserId();
        InternetServicesAvailedId intServiceId = new InternetServicesAvailedId();
        intServiceId.setServiceId(availedServiceId);
        intServiceId.setStartDate(startDate);
        intServiceId.setUserId(userId);
        InternetServiceAvailed availedInternetService = internetServiceAvailedRepo
                .findById(intServiceId)
                .orElseThrow(() -> new ResourceNotFoundException("AvailedInternetService with id " + intServiceId + "doesn't exists"));
        availedInternetService.setActive(false);
        internetServiceAvailedRepo.save(availedInternetService);
    }

    public List<InternetServiceAvailed> findByEndDate(LocalDate todayDate) {
        return internetServiceAvailedRepo.findByEndDate(todayDate);
    }
    
}
