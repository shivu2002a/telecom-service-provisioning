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

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    
    //Create
    //Read
    //Update
    //Delete
    //Read pending requests
    //Update pending requests

    @Autowired
    private PendingRequestService pendingRequestsService;

    @GetMapping("/api/approval-requests")
    public List<PendingRequest> getAllPendingRequests(){ 
        return pendingRequestsService.getAllPendingRequest();
    }
    
    @PatchMapping("/api/approval-requests")
    public List<PendingRequest> updatePendingRequest(){ 
        return pendingRequestsService.getAllPendingRequest();
    }






}
