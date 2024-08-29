package com.telecom.telecom_service_provisioning.repository;

import com.telecom.telecom_service_provisioning.model.TvService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.telecom_service_provisioning.model.PendingRequest;

import java.util.List;

@Repository
public interface PendingRequestRepository extends JpaRepository<PendingRequest, Integer>{
    List<PendingRequest> findByActiveTrue();
}
