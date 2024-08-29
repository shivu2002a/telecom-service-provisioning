package com.telecom.telecom_service_provisioning.dto;

import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.model.User;
import java.time.LocalDate;

import lombok.Data;

@Data
public class TvServiceAvailedResponseObject {
    private Integer serviceId;
    private LocalDate startDate;
    private LocalDate endDate;
    private User user;
    private TvService tvService;
}
