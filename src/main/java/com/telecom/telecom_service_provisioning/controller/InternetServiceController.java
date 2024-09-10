package com.telecom.telecom_service_provisioning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.service.implementations.InternetServiceManager;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/api/internet-services")
@Slf4j
public class InternetServiceController {
    
    //Get all Services
    //Get details of a service
    //subscribe to a service
    @Autowired
    private InternetServiceManager internetService;

    @GetMapping("/")
    public ResponseEntity<List<InternetService>> getAllInternetServices() {
        LOGGER.info("Getting all internet services");
        return new ResponseEntity<>(internetService.getAllInternetService(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<InternetService> getInternetServiceDetailsById(@PathVariable Integer id) throws ResourceNotFoundException {
            return new ResponseEntity<>(internetService.getInternetServiceDetails(id), HttpStatus.OK);
    }

    
    
}
