package com.telecom.telecom_service_provisioning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.telecom_service_provisioning.model.PendingRequest;

@Repository
public interface PendingRequestRepository extends JpaRepository<PendingRequest, Integer>{
    
}
