package com.telecom.telecom_service_provisioning.service.implementations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.List;

import com.telecom.telecom_service_provisioning.dto.TerminationMailDto;
import com.telecom.telecom_service_provisioning.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;

import com.telecom.telecom_service_provisioning.model.compositekey_models.TvServicesAvailedId;
import com.telecom.telecom_service_provisioning.repository.TvServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.service.Interfaces.AvailedTvServiceManagerInterface;

@Service
@Slf4j
public class AvailedTvServiceManager implements AvailedTvServiceManagerInterface {


    @Autowired
    private AuthenticationServiceImpl authService;

    @Autowired
    private TvServiceManager tvService;

    @Autowired
    private EmailMiddlewareService emailService;

    @Autowired
    private TvServiceAvailedRepository availedTvServiceRepo;

    @Autowired
    private EmailMiddlewareService emailMiddlewareService;

    public java.util.List<TvServiceAvailed> getActiveSubscribedServices(Integer userId) {
        return availedTvServiceRepo.findByUserIdAndActiveTrue(userId);
    }
    
    public void deactivateService(Integer serviceId, LocalDate startDate) throws Exception {
        Integer userId = authService.getCurrentUserDetails().getUserId();
        TvServicesAvailedId tvServicesAvailedId = new TvServicesAvailedId();
        tvServicesAvailedId.setUserId(userId);
        tvServicesAvailedId.setStartDate(startDate);
        tvServicesAvailedId.setServiceId(serviceId);
        TvServiceAvailed availedTvService = availedTvServiceRepo
                .findById(tvServicesAvailedId)
                .orElseThrow(() -> new ResourceNotFoundException("AvailedTVService with id " + tvServicesAvailedId + " doesn't exists"));
        availedTvService.setActive(false);
        availedTvService.setEndDate(LocalDate.now());
        availedTvServiceRepo.save(availedTvService);

        sendTerminationMail(availedTvService);
        activateServiceInQueue(userId);
    }

    public void sendTerminationMail(TvServiceAvailed availedTvService) throws ResourceNotFoundException {
        TerminationMailDto dto = new TerminationMailDto();
        User user = authService.getCurrentUserDetails();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setSubject("Service Termination");
        dto.setStartDate(availedTvService.getStartDate());
        dto.setNoOfDaysUsed( (int) (ChronoUnit.DAYS.between(availedTvService.getStartDate(), LocalDate.now())) + 1);
        TvService service =tvService.getTvServiceDetails(availedTvService.getServiceId());
        dto.setServiceName(service.getServiceName());
        dto.setServiceType(service.getServiceType());
        dto.setEndDate(LocalDate.now());
        dto.setServiceValidity(service.getValidity());
        emailMiddlewareService.sendServiceTerminationMail(dto);
    }

    public void activateServiceInQueue(Integer userId) {
        List<TvServiceAvailed> currentServices = availedTvServiceRepo.findByUserIdAndActiveTrue(userId);
        if(currentServices.isEmpty()) return;
        TvServiceAvailed currentService = currentServices.get(0);
        if(currentServices.get(0).getStartDate().isAfter(LocalDate.now())) {
            TvServiceAvailed activateService = new TvServiceAvailed();
            TvServicesAvailedId id = new TvServicesAvailedId();
            id.setServiceId(currentService.getServiceId());
            id.setUserId(userId);
            id.setStartDate(currentService.getStartDate());
            availedTvServiceRepo.deleteById(id);

            activateService.setServiceId(currentService.getServiceId());
            activateService.setUserId(userId);
            activateService.setStartDate(LocalDate.now());
            activateService.setEndDate(LocalDate.now().plusDays(currentService.getTvService().getValidity()));
            activateService.setActive(true);
            availedTvServiceRepo.save(activateService);
        }
    }

    public List<TvServiceAvailed> getInactiveSubscribedServices(Integer userId) {
        return availedTvServiceRepo.findByUserIdAndActiveFalse(userId);
    }
}
