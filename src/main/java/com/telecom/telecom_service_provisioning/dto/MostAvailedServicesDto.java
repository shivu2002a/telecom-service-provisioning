package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;

@Data
public class MostAvailedServicesDto {
    
    private Integer serviceId;

    private String serviceName;

    private String serviceType;

    private Integer subscribedCount;
}
