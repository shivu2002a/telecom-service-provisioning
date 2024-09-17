package com.telecom.telecom_service_provisioning.service.Interfaces;

import java.util.List;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;
import com.telecom.telecom_service_provisioning.dto.ModifySubscription;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.model.User;

public interface TvServiceManagerInterface {

    public List<TvService> getAllTvService();

    public TvService getTvServiceDetails(Integer id);

    public boolean subscribeToTvService(Integer serviceId) throws Exception;

    public void availTvService(Integer userId, Integer serviceId) throws Exception;

    public List<TvService> getTvServicesForUpgradeDowngrade(String serviceName, String serviceType);

    public TvServiceAvailed modifySubscription(ModifySubscription modifySubscription);
}
