package com.telecom.telecom_service_provisioning.service.Interfaces;

import com.telecom.telecom_service_provisioning.dto.FeedbackDto;
import com.telecom.telecom_service_provisioning.dto.MostAvailedServicesDto;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.TvService;
import java.util.*;

public interface AdminService {
    InternetService createInternetService(InternetService internetService);
    
    List<InternetService> getAllInternetService();
    
    void terminateInternetService(Integer id) throws ResourceNotFoundException;
    
    InternetService updateInternetService(InternetService updates);

    // TV Service methods
    TvService createTvService(TvService tvService);
    
    List<TvService> getAllTvServices();
    
    void terminateTvService(Integer id) throws ResourceNotFoundException;
    
    TvService updateTvService(TvService updates);

    // Statistics methods
    List<MostAvailedServicesDto> getMostAvailedInternetService();
    
    List<MostAvailedServicesDto> getMostAvailedTvService();

    // Feedback methods
    FeedbackDto getAllFeedbacks();

}
