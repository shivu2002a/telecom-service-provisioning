package com.telecom.telecom_service_provisioning.service.Interfaces;

import java.time.LocalDate;
import java.util.List;

import com.telecom.telecom_service_provisioning.dto.AvailedServices;
import com.telecom.telecom_service_provisioning.dto.UserDetailsDto;
import com.telecom.telecom_service_provisioning.model.PendingRequest;

/**
 * UserServiceInterface
 */
public interface UserServiceInterface {

    public AvailedServices getAllSubscribedServices();

    public void deactivateInternetService(Integer availedServiceId, LocalDate startDate) throws Exception;

    public void deactivateTvService(Integer availedServiceId, LocalDate startDate) throws Exception;

    public UserDetailsDto getUserDetails() ;

    public List<PendingRequest> getAllPendingRequests();

    public void createTvServiceFeedback(Integer availedTvServiceId, String feedback) throws Exception;

    public void createInternetServiceFeedback(Integer availedInternetServiceId, String feedback) throws Exception;
}