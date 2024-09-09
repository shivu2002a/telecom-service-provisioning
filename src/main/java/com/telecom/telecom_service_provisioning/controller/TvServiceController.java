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
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.service.implementations.TvServiceManager;

import lombok.extern.slf4j.Slf4j;


@RestController
@CrossOrigin
@RequestMapping("/api/tv-services")
@Slf4j
public class TvServiceController {

    //Get all Services
    //Get details of a service
    //subscribe to a service

    @Autowired
    private TvServiceManager tvService;

    @GetMapping("/")
    public ResponseEntity<List<TvService>> getAllInternetServices() {
        LOGGER.info("Getting all the tv services");
        return new ResponseEntity<>(tvService.getAllTvService(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TvService> getTvServiceDetailsById(@PathVariable Integer id) throws ResourceNotFoundException {
        LOGGER.info("Getting all the internet services");
        return new ResponseEntity<>(tvService.getTvServiceDetails(id), HttpStatus.OK);
    }   
}
