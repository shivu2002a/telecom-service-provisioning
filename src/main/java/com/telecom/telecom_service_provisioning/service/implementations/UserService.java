package com.telecom.telecom_service_provisioning.service.implementations;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.dto.AvailedServices;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;

@Service
public class UserService {

    @Autowired
    private AvailedInternetServiceManager availedInternetService;

    @Autowired
    private AuthenticationServiceImpl authService;

    @Autowired
    private AvailedTvServiceManager availedTvService;

    public AvailedServices getAllSubscribedServices() {
        Integer userId = authService.getCurrentUserDetails().getUserId();
        java.util.List<InternetServiceAvailed> availedInternetServices = availedInternetService.getActiveSubscribedServices(userId);
        java.util.List<TvServiceAvailed> availedTvServices = availedTvService.getActiveSubscribedServices(userId);
        
        AvailedServices availedServices = new AvailedServices();
        if (availedInternetServices.isEmpty() && availedTvServices.isEmpty()) {
            return availedServices;
        }
        availedServices.setInternetServicesAvailed(availedInternetServices); 
        availedServices.setTvServicesAvailed(availedTvServices); 
        return availedServices;
    }

    public void deactivateInternetService(Integer availedServiceId, LocalDate startDate) throws Exception {
        availedInternetService.deactivateService(availedServiceId, startDate);
    }

    public void deactivateTvService(Integer availedServiceId, LocalDate startDate)throws Exception{
        availedTvService.deactivateService(availedServiceId, startDate);
    }
    
}
