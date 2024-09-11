package com.telecom.telecom_service_provisioning.service.implementations;

import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.model.compositekey_models.InternetServicesAvailedId;
import com.telecom.telecom_service_provisioning.model.compositekey_models.TvServicesAvailedId;
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
        availedInternetService.setEndDate(LocalDate.now());
        internetServiceAvailedRepo.save(availedInternetService);
        activateServiceInQueue(userId);
    }

    public void activateServiceInQueue(Integer userId) {
        List<InternetServiceAvailed> currentServices = internetServiceAvailedRepo.findByUserIdAndActiveTrue(userId);
        if(currentServices.isEmpty()) return;
        InternetServiceAvailed currentService = currentServices.get(0);
        if(currentService.getStartDate().isAfter(LocalDate.now())) {
            InternetServicesAvailedId id = new InternetServicesAvailedId();
            id.setServiceId(currentService.getServiceId());
            id.setUserId(userId);
            id.setStartDate(currentService.getStartDate());
            internetServiceAvailedRepo.deleteById(id);

            InternetServiceAvailed activateService = new InternetServiceAvailed();
            activateService.setServiceId(currentService.getServiceId());
            activateService.setUserId(userId);
            activateService.setStartDate(LocalDate.now());
            activateService.setEndDate(LocalDate.now().plusDays(currentService.getInternetService().getValidity()));
            activateService.setActive(true);
            internetServiceAvailedRepo.save(activateService);
        }
    }

    public List<InternetServiceAvailed> findByEndDate(LocalDate todayDate) {
        return internetServiceAvailedRepo.findByEndDate(todayDate);
    }
    
}
