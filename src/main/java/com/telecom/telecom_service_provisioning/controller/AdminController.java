package com.telecom.telecom_service_provisioning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.service.PendingRequestService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
    private PendingRequestService pendingRequestService;

    @GetMapping("/api/approval-requests")
    public List<PendingRequest> getAllPendingRequests(){ 
        return pendingRequestService.getAllPendingRequest();
    }
    
    @PatchMapping("/api/approval-requests")
    public void updatePendingRequest(@RequestBody PendingRequest pendingRequest){ 
        pendingRequestService.updatePendingRequest(pendingRequest);
    }






}
