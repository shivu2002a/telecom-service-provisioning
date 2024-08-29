package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;

@Data
public class TvServiceResponseObject {

    private Integer serviceId;
    private String serviceName;
    private String description;
    private String benefits;
    private String criteria;
    private Boolean active;
    private Double monthlyCost;
}
