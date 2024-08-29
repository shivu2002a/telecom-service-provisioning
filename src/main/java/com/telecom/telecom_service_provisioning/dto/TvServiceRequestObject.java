package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;

@Data
public class TvServiceRequestObject {
    private String serviceName;
    private String description;

    private String benefits;

    private String criteria;

    private Double monthlyCost;
}
