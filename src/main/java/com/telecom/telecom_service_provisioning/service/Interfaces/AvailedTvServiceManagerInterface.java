package com.telecom.telecom_service_provisioning.service.Interfaces;

import java.time.LocalDate;

import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;

public interface AvailedTvServiceManagerInterface {
    
    public java.util.List<TvServiceAvailed> getActiveSubscribedServices(Integer userId);
    
    public void deactivateService(Integer serviceId, LocalDate startDate) throws Exception;

    public void activateServiceInQueue(Integer userId);
}
