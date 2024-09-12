package com.telecom.telecom_service_provisioning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.telecom.telecom_service_provisioning.dto.FeedbackDto;
import com.telecom.telecom_service_provisioning.dto.MostAvailedServicesDto;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.service.implementations.AdminServiceImpl;
import com.telecom.telecom_service_provisioning.service.implementations.PendingRequestServiceImpl;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/admin")
@CrossOrigin
@Slf4j
public class AdminController {
    
    // Create a service
    // Read services
    // Update a service
    // Delete a service
    // Read pending requests
    // Update pending requests
    // Get most subscribed tv services
    // Get most subscribed internet services

    @Autowired
    private PendingRequestServiceImpl pendingRequestService;

    @Autowired
    private AdminServiceImpl adminService;


    //Add Internet Services
    @PostMapping("/api/internet-service")
    public ResponseEntity<InternetService> addService(@RequestBody InternetService service) {
        System.out.println(service);
        InternetService createdService = adminService.createInternetService(service);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }

    //Get all active Internet Services
    @GetMapping("/api/internet-service")
    public ResponseEntity<List<InternetService>> getAllInternetServices() {
        return new ResponseEntity<List<InternetService>>(adminService.getAllInternetService(), HttpStatus.OK);
    }

    //Update Internet Service
    @PatchMapping("/api/internet-service")
    public ResponseEntity<InternetService> updateInternetServices(@RequestBody InternetService updates) {
        InternetService updatedInternetService = adminService.updateInternetService(updates);
        return new ResponseEntity<InternetService>(updatedInternetService, HttpStatus.CREATED);
    }


    //Terminate Internet Services
    @DeleteMapping("/api/internet-service")
    public ResponseEntity<String> terminateInternetServices(@RequestParam Integer id) throws ResourceNotFoundException {
        adminService.terminateInternetService(id);
        return new ResponseEntity<String>("Terminated Successfully", HttpStatus.OK);
        
    }

    //Add Tv Service
    @PostMapping("/api/tv-service")
    public ResponseEntity<TvService> addTvService(@RequestBody TvService service) {
        System.out.println(service);
        return new ResponseEntity<>(adminService.createTvService(service), HttpStatus.CREATED);
    }

    //Get all active Tv Services
    @GetMapping("/api/tv-service")
    public ResponseEntity<List<TvService>> getAllTvServices() {
        LOGGER.info("Retrieving all the tv services");
        return new ResponseEntity<>(adminService.getAllTvServices(), HttpStatus.OK);
    }

    //Update Tv Service
    @PatchMapping("/api/tv-service")
    public ResponseEntity<TvService> updateTvServices(@RequestBody TvService updates) {
        LOGGER.info("Update tv service: Updating tv service: {}", updates);
        TvService updatedTvService = adminService.updateTvService(updates);
        return new ResponseEntity<>(updatedTvService, HttpStatus.CREATED);
    }


    //Terminate Tv Services
    @DeleteMapping("/api/tv-service")
    public ResponseEntity<String> terminateTvServices(@RequestParam Integer id) throws ResourceNotFoundException {
        LOGGER.info("Terminating the tv service");
        adminService.terminateTvService(id);
        return new ResponseEntity<>("Terminated Successfully", HttpStatus.OK);
    }
    

    @PatchMapping("/api/approval-requests")
    public ResponseEntity<PendingRequest> updatePendingRequest(@RequestBody PendingRequest pendingRequest) throws Exception {
        LOGGER.info("Updating pending request");
        PendingRequest updaPendingRequest = pendingRequestService.updatePendingRequest(pendingRequest);
        return new ResponseEntity<>(updaPendingRequest, HttpStatus.OK);
    }

    @GetMapping("/api/approval-requests")
    public ResponseEntity<List<PendingRequest>> getAllPendingRequests(){
        LOGGER.info("Calling getAllpendingRequests service");
        return new ResponseEntity<>(pendingRequestService.getAllPendingRequest(), HttpStatus.OK);
    }

    @GetMapping("/api/most-availed-internet-services")
    public ResponseEntity<List<MostAvailedServicesDto>> mostAvailedInternetServices() {
        LOGGER.info("Retrieving most availed internet services");
        return ResponseEntity.ok(adminService.getMostAvailedInternetService());
    }

    @GetMapping("/api/most-availed-tv-services")
    public ResponseEntity<List<MostAvailedServicesDto>> mostAvailedTvServices() {
        LOGGER.info("Retrieving most availed tv services");
        return ResponseEntity.ok(adminService.getMostAvailedTvService());
    }

    @GetMapping("/api/feedbacks")
    public ResponseEntity<FeedbackDto> getFeedbacks() {
        LOGGER.info("Retrieving all the feedbacks");
        return ResponseEntity.ok(adminService.getAllFeedbacks());
    }
}
