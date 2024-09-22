package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TerminationMailDto {
    private String email;
    private String username, subject;

    private String serviceName, serviceType;
    private Integer serviceValidity;

    private LocalDate startDate, endDate;

    private Integer noOfDaysUsed;

}
