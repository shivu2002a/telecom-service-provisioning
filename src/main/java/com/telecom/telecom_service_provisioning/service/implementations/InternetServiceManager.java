package com.telecom.telecom_service_provisioning.service.implementations;

import java.time.LocalDate;
import java.util.List;

import com.telecom.telecom_service_provisioning.dto.ActivationMailDto;
import com.telecom.telecom_service_provisioning.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;
import com.telecom.telecom_service_provisioning.dto.ModifySubscription;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.MaxServicesAlreadyAvailedException;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.repository.InternetServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.InternetServiceRepository;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;
import com.telecom.telecom_service_provisioning.service.Interfaces.InternetServiceManagerInterface;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InternetServiceManager implements InternetServiceManagerInterface {

    @Autowired
    private AuthenticationServiceImpl authService;

    @Autowired
    private InternetServiceRepository internetServiceRepo;

    @Autowired
    private PendingRequestRepository pendingRequestRepo;

    @Autowired
    private EmailMiddlewareService emailMiddlewareService;

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

    public boolean subscribeToService(Integer serviceId) throws Exception {
        InternetService service = internetServiceRepo
                                    .findById(serviceId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Service with id: " + serviceId + " doesn't exists"));
        Integer userId = authService.getCurrentUserDetails().getUserId();
        if (service.getCriteria() == null || service.getCriteria().isEmpty()) {
            // Direct subscription
            if (internetServiceAvailedRepo.findByUserIdAndActiveTrue(serviceId).size() >= 2) {
                throw new MaxServicesAlreadyAvailedException("Already availed/requested 2 Tv services");
            }
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

    public void availInternetService(Integer userId, Integer serviceId) throws Exception {
        InternetService toSubscribeService = internetServiceRepo.findById(serviceId).get();
        List<InternetServiceAvailed> currentservices = internetServiceAvailedRepo.findByUserIdAndActiveTrue(userId);
        LOGGER.info("Currently availed internet services: {}", currentservices);
        int availedServices = currentservices.size(); 
        int pendingRequests = pendingRequestRepo.findByUserIdAndServiceTypeAndActiveTrue(userId, PendingRequestServiceType.INTERNET_SERVICE).size();
        if( pendingRequests + availedServices >= 2) {
            throw new MaxServicesAlreadyAvailedException("Already availed/requested 2 Internet services");
        }
        InternetServiceAvailed availed = new InternetServiceAvailed();
        availed.setUserId(userId);
        availed.setServiceId(serviceId);
        availed.setInternetService(internetServiceRepo.findById(serviceId).get());
        if(availedServices == 0) {
            availed.setStartDate(LocalDate.now());
            availed.setEndDate(LocalDate.now().plusDays(toSubscribeService.getValidity()));
        } else {
            availed.setStartDate(currentservices.get(0).getEndDate());
            availed.setEndDate(currentservices.get(0).getEndDate().plusDays(toSubscribeService.getValidity()));
        }
        availed.setActive(true);
        internetServiceAvailedRepo.save(availed);
        sendSubscribedMail(toSubscribeService, availed);
    }

    public void sendSubscribedMail(InternetService service, InternetServiceAvailed availed)  {
        ActivationMailDto dto = new ActivationMailDto();
        User user = authService.getCurrentUserDetails();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setSubject("Internet Service Subscribed");

        dto.setServiceName(service.getServiceName());
        dto.setServiceType(service.getServiceType());
        dto.setServiceBenefits(service.getBenefits());
        dto.setServiceDescription(service.getDescription());
        dto.setServiceCost(service.getCost());
        dto.setServiceValidity(service.getValidity());
        dto.setStartDate(availed.getStartDate());
        dto.setEndDate(availed.getEndDate());
        emailMiddlewareService.sendServiceActivationMail(dto);
    }

    public List<InternetService> getInternetServicesForUpgradeDowngrade(String serviceName, String serviceType) {
        return internetServiceRepo.findByActiveTrueAndServiceNameAndServiceTypeNot(serviceName,serviceType);
    }

    public InternetServiceAvailed modifySubscription(ModifySubscription modifySubscription) {
        Integer userId = authService.getCurrentUserDetails().getUserId();
        Integer oldServiceId = modifySubscription.getOldServiceId();
        LocalDate oldDate = modifySubscription.getStartDate();
        InternetServiceAvailed old = internetServiceAvailedRepo.findByCompositeKeyAndActiveTrue(userId, oldServiceId, oldDate).get();
        old.setActive(false);
        old.setEndDate(LocalDate.now());
        internetServiceAvailedRepo.save(old);

        InternetServiceAvailed updatedAvail = new InternetServiceAvailed();
        updatedAvail.setUserId(userId);
        updatedAvail.setServiceId(modifySubscription.getNewServiceId());
        updatedAvail.setStartDate(oldDate);
        updatedAvail.setActive(true);
        updatedAvail.setEndDate(modifySubscription.getEndDate());
        updatedAvail.setInternetService(internetServiceRepo.findById(modifySubscription.getNewServiceId()).get());
        return internetServiceAvailedRepo.save(updatedAvail);
    }
}
