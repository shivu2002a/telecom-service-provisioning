package com.telecom.telecom_service_provisioning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.model.PendingRequest;

@Repository
public interface PendingRequestRepository extends JpaRepository<PendingRequest, Integer>{
    
    List<PendingRequest> findByActiveTrue();

    List<PendingRequest> findByUserId(Integer userId);

    List<PendingRequest> findByUserIdAndServiceTypeAndActiveTrue(Integer userId, PendingRequestServiceType serviceType);
    
}
