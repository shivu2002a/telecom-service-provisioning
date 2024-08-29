package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;

@Data
public class InternetServiceResponseObject {
    
    private String serviceName;
    private String description;
    private String serviceType;
    private Integer serviceDownloadSpeed;
    private Integer serviceUploadSpeed;
    private String benefits;
    private String criteria;
    private Double monthlyCost;
    private Boolean active;

}
