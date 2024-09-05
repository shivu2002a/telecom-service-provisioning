package com.telecom.telecom_service_provisioning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.telecom_service_provisioning.model.InternetService;

@Repository
public interface InternetServiceRepository extends JpaRepository<InternetService, Integer>{
    
    List<InternetService> findByActiveTrue();

    List<InternetService> findByActiveTrueAndServiceNameAndServiceTypeNot(String serviceName, String serviceType);

}
