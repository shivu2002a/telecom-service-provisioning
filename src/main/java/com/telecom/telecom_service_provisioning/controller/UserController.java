package com.telecom.telecom_service_provisioning.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.telecom_service_provisioning.dto.AvailedServices;
import com.telecom.telecom_service_provisioning.dto.ModifySubscription;
import com.telecom.telecom_service_provisioning.dto.UserDetailsDto;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.service.implementations.InternetServiceManager;
import com.telecom.telecom_service_provisioning.service.implementations.TvServiceManager;
import com.telecom.telecom_service_provisioning.service.implementations.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/user")
@Slf4j
public class UserController {
    
    // Get subscribed services
    // Upgrade 
    // Downgrade
    // Terminate service

    @Autowired
    private UserService userService;

    @Autowired
    private InternetServiceManager internetService;

    @Autowired
    private TvServiceManager tvService;

    @PostMapping("/api/internet-service")
    public ResponseEntity<String> subscribeToInternetService(@RequestParam Integer serviceId) throws Exception {
        boolean availed = internetService.subscribeToService(serviceId);
        LOGGER.info("Subscribe to internet service with id: {}", serviceId);
        if(availed) {
            return new ResponseEntity<>("Successfully subscribed", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Requested for admins approval", HttpStatus.OK);
        }
    }

    @PostMapping("/api/tv-service")
    public ResponseEntity<String> subscribeToTvService(@RequestParam Integer serviceId) throws Exception {
        boolean availed = tvService.subscribeToTvService(serviceId);
        LOGGER.info("Subscribe to tv service with id: {}", serviceId);

        if(availed) {
            return new ResponseEntity<>("Successfully subscribed", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Requested for admins approval", HttpStatus.OK);
        }
    }

    @GetMapping("/api/subscribed-services")
    public ResponseEntity<AvailedServices> getSubscribedServices() {
        LOGGER.info("Get all subscribe services for user.");
        AvailedServices allSubscribedServices = userService.getAllSubscribedServices();
        if (allSubscribedServices.getInternetServicesAvailed() == null && allSubscribedServices.getTvServicesAvailed() == null ) {
            return new ResponseEntity<>(allSubscribedServices, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allSubscribedServices, HttpStatus.OK);
    }

    @GetMapping("/api/subscribed-services/inactive")
    public ResponseEntity<AvailedServices> getPrevSubscribedServices() {
        LOGGER.info("Retrieving all previously subscribed services !!");
        AvailedServices allPrevSubscribedServices = userService.getAllPrevSubscribedServices();
        if (allPrevSubscribedServices.getInternetServicesAvailed() == null && allPrevSubscribedServices.getTvServicesAvailed() == null ) {
            return new ResponseEntity<>(allPrevSubscribedServices, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allPrevSubscribedServices, HttpStatus.OK);
    }

    @GetMapping("/api/internet-service/other")
    public ResponseEntity<List<InternetService>> getAvailableInternetServicesForUpgradeOrDowngrade(@RequestParam String serviceName, @RequestParam String serviceType) {
        LOGGER.info("Alternatives for up/down grading a internet service.");
        List<InternetService> existingServicesForModification = internetService.getInternetServicesForUpgradeDowngrade(serviceName, serviceType);
        return new ResponseEntity<>(existingServicesForModification, HttpStatus.OK);
    }

    @GetMapping("/api/tv-service/other")
    public ResponseEntity<List<TvService>> getAvailableTvServicesForUpgradeOrDowngrade(@RequestParam String serviceName, @RequestParam String serviceType) {
        LOGGER.info("Alternatives for up/down grading a tv service.");
        List<TvService> existingServicesForModification = tvService.getTvServicesForUpgradeDowngrade(serviceName, serviceType);
        return new ResponseEntity<>(existingServicesForModification, HttpStatus.OK);
    }

    @PutMapping("/api/internet-service")
    public ResponseEntity<InternetServiceAvailed> upgradeDowngradeInternetSubcription(@RequestBody ModifySubscription modifySubscription) {
        LOGGER.info("Modifying availed internet service.");
        InternetServiceAvailed modification = internetService.modifySubscription(modifySubscription);
        return new ResponseEntity<>(modification, HttpStatus.OK);
    }

    @PutMapping("/api/tv-service")
    public ResponseEntity<TvServiceAvailed> upgradeDowngradeTvSubcription(@RequestBody ModifySubscription modifySubscription) {
        LOGGER.info("Modifying availed tv service");
        TvServiceAvailed modification = tvService.modifySubscription(modifySubscription);
        return new ResponseEntity<>(modification, HttpStatus.OK);
    }

    @DeleteMapping("/api/internet-service")
    public ResponseEntity<String> deactivateInternetService(@RequestParam Integer availedServiceId, @RequestParam LocalDate startDate) throws Exception {
        LOGGER.info("Deactivating internet service with id: {}", availedServiceId);
        userService.deactivateInternetService(availedServiceId, startDate);
        return ResponseEntity.ok("Internet service deactivated successfully");
    }

    @DeleteMapping("/api/tv-service")
    public ResponseEntity<String> deactivateTvService(@RequestParam Integer availedServiceId, @RequestParam LocalDate startDate) throws Exception {
        LOGGER.info("Deactivating tv service with id: {}", availedServiceId);
        userService.deactivateTvService(availedServiceId, startDate);
        return ResponseEntity.ok("TV service deactivated successfully");
    }

    @GetMapping("/api/user-details")
    public ResponseEntity<UserDetailsDto> getCurrentUserDetails() {
        LOGGER.info("Retrieving user details");
        return ResponseEntity.ok(userService.getUserDetails());
    }    

    @GetMapping("/api/pending-request")
    public ResponseEntity<List<PendingRequest>> getPendingRequestsOfUser() {
        LOGGER.info("Retrieving user details");
        return ResponseEntity.ok(userService.getAllPendingRequests());
    }

    @PostMapping("/api/internet-service/feedback")
    public ResponseEntity<String> postInternetServiceFeedback(@RequestParam Integer availedServiceId, @RequestParam String feedback) throws Exception {
        LOGGER.info("Creating feedback");
        userService.createInternetServiceFeedback(availedServiceId, feedback);
        return ResponseEntity.ok("Internet service feedback created successfully");
    }

    @PostMapping("/api/tv-service/feedback")
    public ResponseEntity<String> postTvServiceFeedback(@RequestParam Integer availedServiceId, @RequestParam String feedback) throws Exception {
        LOGGER.info("Creating feedback");
        userService.createTvServiceFeedback(availedServiceId, feedback);
        return ResponseEntity.ok("Tv service Feedback created successfully");
    }

}
