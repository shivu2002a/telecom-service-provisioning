package com.telecom.telecom_service_provisioning.service.Interfaces;

import com.telecom.telecom_service_provisioning.dto.FeedbackDto;

/**
 * FeedbackServiceInterface
 */
public interface FeedbackServiceInterface {

    public void createInternetServiceFeedback(Integer availedServiceId, String feedback) throws Exception;

    public void createTvServiceFeedback(Integer availedServiceId, String feedback) throws Exception;

    public FeedbackDto getAllFeedbacks();    
}