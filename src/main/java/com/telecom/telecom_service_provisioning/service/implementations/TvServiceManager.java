package com.telecom.telecom_service_provisioning.service.implementations;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;
import com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions.ServiceAlreadyAvailedException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceRepository;

@Service
public class TvServiceManager {
    
    @Autowired
    private AuthenticationServiceImpl authService;

    @Autowired
    private TvServiceRepository tvServiceRepo;

    @Autowired
    private PendingRequestRepository pendingRequestRepo;

    @Autowired
    private TvServiceAvailedRepository tvServiceAvailedRepo;

    public List<TvService> getAllTvService(){
        return tvServiceRepo.findByActiveTrue();
    }

    public TvService getTvServiceDetails(Integer id){
        return tvServiceRepo.findById(id).get();
    }

    public boolean subscribeToTvService(Integer serviceId) throws Exception {
        TvService service = tvServiceRepo
                            .findById(serviceId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tv Service with id: " + serviceId + "doesn't exists !!"));
        if (service.getCriteria() == null || service.getCriteria().isEmpty()) {
            // Direct subscription
            Integer userId = authService.getCurrentUserDetails().getUserId();
            availTvService(userId, serviceId);
            return true;
        } else {
            // Create pending request
            createPendingRequest(serviceId);
            return false;
        }
    }

    private void createPendingRequest(Integer serviceId) {
        PendingRequest request = new PendingRequest();
        User currentUser = authService.getCurrentUserDetails();
        request.setUserId(currentUser.getUserId());
        request.setServiceId(serviceId);
        request.setServiceType(PendingRequestServiceType.TV_SERVICE);
        request.setRequestStatus(PendingRequestStatus.REQUESTED);
        request.setRemarks("Awaiting approval based on criteria: " + tvServiceRepo.findById(serviceId).get().getCriteria());
        request.setActive(true);
        pendingRequestRepo.save(request);
    }

    public void availTvService(Integer userId, Integer serviceId) throws Exception {
        List<TvServiceAvailed> availedServices = tvServiceAvailedRepo.findByUserId(userId);
        TvService toSubscribeService = tvServiceRepo.findById(serviceId).get();
        for (TvServiceAvailed service : availedServices) {
            if (service.getTvService().getServiceName().equals(toSubscribeService.getServiceName())) {
                throw new ServiceAlreadyAvailedException("Tv service: " + toSubscribeService.getServiceName() + " already availed");
            }
        }
        TvServiceAvailed availed = new TvServiceAvailed();
        availed.setUserId(userId);
        availed.setServiceId(serviceId);
        availed.setStartDate(LocalDate.now());
        availed.setEndDate(LocalDate.now().plusMonths(1));
        availed.setUser(authService.getUserDetailsByUserId(userId));
        availed.setActive(true);
        tvServiceAvailedRepo.save(availed);
    }

    public List<TvService> getTvServicesForUpgradeDowngrade(String serviceName, String serviceType) {
        return tvServiceRepo.findByActiveTrueAndServiceNameAndServiceTypeNot(serviceName,serviceType);
    }
}
