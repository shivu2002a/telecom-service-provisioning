package com.telecom.telecom_service_provisioning.service.implementations;

import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;

import com.telecom.telecom_service_provisioning.dto.TerminationMailDto;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.compositekey_models.InternetServicesAvailedId;
import com.telecom.telecom_service_provisioning.repository.InternetServiceAvailedRepository;

@Slf4j
@Service
public class AvailedInternetServiceManager implements com.telecom.telecom_service_provisioning.service.Interfaces.AvailedInternetServiceManager {

    @Autowired
    private AuthenticationServiceImpl authService;

    @Autowired
    private InternetServiceManager internetService;

    @Autowired
    private EmailMiddlewareService emailMiddlewareService;

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

        sendTerminationMail(availedInternetService);
        activateServiceInQueue(userId);
    }

    public void sendTerminationMail(InternetServiceAvailed availedInternetService) throws ResourceNotFoundException {
        TerminationMailDto dto = new TerminationMailDto();
        User user = authService.getCurrentUserDetails();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setSubject("Service Termination");
        dto.setStartDate(availedInternetService.getStartDate());
        dto.setNoOfDaysUsed( (int) ChronoUnit.DAYS.between(availedInternetService.getStartDate(), LocalDate.now()) + 1);
        InternetService service =internetService.getInternetServiceDetails(availedInternetService.getServiceId());
        dto.setServiceName(service.getServiceName());
        dto.setServiceType(service.getServiceType());
        dto.setEndDate(LocalDate.now());
        dto.setServiceValidity(service.getValidity());
        emailMiddlewareService.sendServiceTerminationMail(dto);
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

    public List<InternetServiceAvailed> getInactiveSubscribedServices(Integer userId) {
        return internetServiceAvailedRepo.findByUserIdAndActiveFalse(userId);
    }
    
}
