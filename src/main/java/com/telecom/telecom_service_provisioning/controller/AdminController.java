package com.telecom.telecom_service_provisioning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.service.implementations.AdminServiceImpl;
import com.telecom.telecom_service_provisioning.service.implementations.PendingRequestServiceImpl;


@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    
    // Create a service
    // Read services
    // Update a service
    // Delete a service
    // Read pending requests
    // Update pending requests

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
    @PostMapping("/api/internet-service")
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
        return new ResponseEntity<>(adminService.getAllTvServices(), HttpStatus.OK);
    }

    //Update Tv Service
    @PatchMapping("/api/tv-service")
    public ResponseEntity<TvService> updateTvServices(@RequestBody TvService updates) {
        TvService updatedTvService = adminService.updateTvService(updates);
        return new ResponseEntity<>(updatedTvService, HttpStatus.CREATED);
    }


    //Terminate Tv Services
    @PatchMapping("/api/tv-service")
    public ResponseEntity<String> terminateTvServices(@RequestParam Integer id) throws ResourceNotFoundException {
        adminService.terminateTvService(id);
        return new ResponseEntity<>("Terminated Successfully", HttpStatus.OK);
    }
    

    @PatchMapping("/api/approval-requests")
    public ResponseEntity<PendingRequest> updatePendingRequest(@RequestBody PendingRequest pendingRequest) throws ResourceNotFoundException{
        PendingRequest updaPendingRequest = pendingRequestService.updatePendingRequest(pendingRequest);
        return new ResponseEntity<>(updaPendingRequest, HttpStatus.OK);
    }


    @GetMapping("/api/approval-requests")
    public ResponseEntity<List<PendingRequest>> getAllPendingRequests(){ 
        return new ResponseEntity<>(pendingRequestService.getAllPendingRequest(), HttpStatus.OK);
    }
}
