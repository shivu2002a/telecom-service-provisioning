package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ActivationMailDto {
    private String email;
    private String username, subject;


    private String serviceName, serviceType;
    private String serviceBenefits, serviceDescription;
    private Double serviceCost;
    private Integer serviceValidity;

    private LocalDate startDate, endDate;
}
