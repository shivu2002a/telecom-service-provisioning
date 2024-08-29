package com.telecom.telecom_service_provisioning.dto;

import java.time.LocalDate;

import com.telecom.telecom_service_provisioning.model.TvService;

import lombok.Data;

@Data
public class InternetServiceAvailedResponseObject {
    private Integer serviceId;
    private LocalDate startDate;
    private LocalDate endDate;
    private TvService tvService;
}
