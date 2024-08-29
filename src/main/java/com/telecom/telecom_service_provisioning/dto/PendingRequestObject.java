package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;

@Data
public class PendingRequestObject {
    private Integer requestId;
    private Integer serviceId;
    private String serviceName;
    private Integer userId;
    private String serviceType;
    private String requestStatus;
    private String remarks;
    private Boolean active;

}
