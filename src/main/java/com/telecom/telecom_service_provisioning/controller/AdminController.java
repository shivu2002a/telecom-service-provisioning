package com.telecom.telecom_service_provisioning.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.repository.InternetServiceRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceRepository;
import com.telecom.telecom_service_provisioning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.service.PendingRequestService;

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
    private AdminService adminService;

    @Autowired
    private InternetServiceRepository internetServiceRepo;

    @Autowired
    private PendingRequestService pendingRequestService;

    @Autowired
    private TvServiceRepository tvServiceRepository;


    //Add Internet Services
    @PostMapping("/internet-service")
    public InternetService addService(@RequestBody InternetService service) {
        System.out.println(service);
        return adminService.createInternetService(service);
    }

    //Get all active Internet Services
    @GetMapping("/internet-service")
    public List<InternetService> getAllInternetServices() {
        return adminService.getAllInternetService();
    }

    //Update Internet Service
    @PatchMapping("/internet-service/update/{id}")
    public InternetService updateInternetServices(@PathVariable Integer id,@RequestBody Map<String,Object> updates) {
        Optional<InternetService> optionalService = internetServiceRepo.findById(id);
        InternetService existingService = optionalService.get();
        existingService.setActive(false);
        internetServiceRepo.save(existingService);
        InternetService updatedService=new InternetService();
        updatedService.setServiceName(existingService.getServiceName());
        updatedService.setDescription(existingService.getDescription());
        updatedService.setServiceType(existingService.getServiceType());
        updatedService.setServiceDownloadSpeed(existingService.getServiceDownloadSpeed());
        updatedService.setServiceUploadSpeed(existingService.getServiceUploadSpeed());
        updatedService.setBenefits(existingService.getBenefits());
        updatedService.setCriteria(existingService.getCriteria());
        updatedService.setMonthlyCost(existingService.getMonthlyCost());
        updatedService.setActive(true);
        return adminService.updatePartialInternetService(updates,updatedService);
    }


    //Terminate Internet Services
    @PatchMapping("/internet-service/terminate/{id}")
    public InternetService terminateInternetServices(@PathVariable Integer id) {
        // Find the service by ID
        Optional<InternetService> optionalService=internetServiceRepo.findById(id);

        return adminService.terminateInternetService(optionalService.get());
    }

    //Add Tv Service
    @PostMapping("/tv-service")
    public TvService addTvService(@RequestBody TvService service) {
        System.out.println(service);
        return adminService.createTvService(service);
    }

    //Get all active Tv Services
    @GetMapping("/tv-service")
    public List<TvService> getAllTvServices() {
        return adminService.getAllTvService();
    }

    //Update Tv Service
    @PatchMapping("/tv-service/update/{id}")
    public TvService updateTvServices(@PathVariable Integer id,@RequestBody Map<String,Object> updates) {
        Optional<TvService> optionalService = tvServiceRepository.findById(id);
        TvService existingService = optionalService.get();
        existingService.setActive(false);
        tvServiceRepository.save(existingService);
        TvService updatedService=new TvService();
        updatedService.setServiceName(existingService.getServiceName());
        updatedService.setDescription(existingService.getDescription());
        updatedService.setBenefits(existingService.getBenefits());
        updatedService.setCriteria(existingService.getCriteria());
        updatedService.setMonthlyCost(existingService.getMonthlyCost());
        updatedService.setActive(true);
        return adminService.updatePartialTvService(updates,updatedService);
    }


    //Terminate Tv Services
    @PatchMapping("/tv-service/terminate/{id}")
    public TvService terminateTvServices(@PathVariable Integer id) {
        // Find the service by ID
        Optional<TvService> optionalService=tvServiceRepository.findById(id);

        return adminService.terminateTvService(optionalService.get());
    }






    @GetMapping("/api/approval-requests")
    public List<PendingRequest> getAllPendingRequests(){ 
        return pendingRequestService.getAllPendingRequest();
    }
    
    @PatchMapping("/api/approval-requests")
    public void updatePendingRequest(@RequestBody PendingRequest pendingRequest,String requestStatus,String remarks){
        pendingRequestService.updatePendingRequest(pendingRequest,requestStatus,remarks);
    }






}
