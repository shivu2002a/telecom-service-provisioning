package com.telecom.telecom_service_provisioning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.telecom_service_provisioning.model.InternetService;

import java.util.List;

@Repository
public interface InternetServiceRepository extends JpaRepository<InternetService, Integer>{
    List<InternetService> findByActiveTrue();
}
