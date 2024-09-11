package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ModifySubscription {
    
    private LocalDate startDate, endDate;
    
    private Integer oldServiceId, newServiceId;   

}
