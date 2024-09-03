package com.telecom.telecom_service_provisioning.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.telecom_service_provisioning.dto.AvailedServices;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.service.implementations.InternetServiceManager;
import com.telecom.telecom_service_provisioning.service.implementations.TvServiceManager;
import com.telecom.telecom_service_provisioning.service.implementations.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
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

    @GetMapping("/api/subscribed-service")
    public ResponseEntity<AvailedServices> getSubscribedServices() {
        AvailedServices allSubscribedServices = userService.getAllSubscribedServices();
        if (allSubscribedServices.getInternetServicesAvailed() == null && allSubscribedServices.getTvServicesAvailed() == null ) {
            return new ResponseEntity<>(allSubscribedServices, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allSubscribedServices, HttpStatus.OK);
    }

    @GetMapping("/api/modify-internet-subscription")
    public ResponseEntity<List<InternetService>> getAvailableInternetServicesForUpgradeOrDowngrade(@RequestParam String serviceName, @RequestParam String serviceType) {
        List<InternetService> existingServicesForModification = internetService.getInternetServicesForUpgradeDowngrade(serviceName, serviceType);
        return new ResponseEntity<>(existingServicesForModification, HttpStatus.OK);
    }

    @GetMapping("/api/modify-tv-subscription")
    public ResponseEntity<List<TvService>> getAvailableTvServicesForUpgradeOrDowngrade(@RequestParam String serviceName, @RequestParam String serviceType) {
        List<TvService> existingServicesForModification = tvService.getTvServicesForUpgradeDowngrade(serviceName, serviceType);
        return new ResponseEntity<>(existingServicesForModification, HttpStatus.OK);
    }

    @PostMapping("/api/deactivate-internet-service")
    public ResponseEntity<String> deactivateInternetService(@RequestParam Integer availedServiceId, @RequestParam LocalDate startDate) throws Exception {
        userService.deactivateInternetService(availedServiceId, startDate);
        return ResponseEntity.ok("Internet service deactivated successfully");
    }

    @PostMapping("/api/deactivate-tv-service")
    public ResponseEntity<String> deactivateTvService(@RequestParam Integer availedServiceId, @RequestParam LocalDate startDate) throws Exception {
        userService.deactivateTvService(availedServiceId, startDate);
        return ResponseEntity.ok("TV service deactivated successfully");
    }



    
}
