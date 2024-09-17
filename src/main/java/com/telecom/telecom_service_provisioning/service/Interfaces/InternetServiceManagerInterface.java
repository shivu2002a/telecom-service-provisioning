package com.telecom.telecom_service_provisioning.service.Interfaces;

import java.util.List;

import com.telecom.telecom_service_provisioning.dto.ModifySubscription;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;

/**
 * InternetServiceManagerInterface
 */
public interface InternetServiceManagerInterface {

    public List<InternetService> getAllInternetService();

    public InternetService getInternetServiceDetails(Integer id) throws ResourceNotFoundException;

    public boolean subscribeToService(Integer serviceId) throws Exception;

    public void createPendingRequest(Integer serviceId);

    public void availInternetService(Integer userId, Integer serviceId) throws Exception;

    public List<InternetService> getInternetServicesForUpgradeDowngrade(String serviceName, String serviceType);

    public InternetServiceAvailed modifySubscription(ModifySubscription modifySubscription);
    
}