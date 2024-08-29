package com.telecom.telecom_service_provisioning.repository;

import com.telecom.telecom_service_provisioning.model.InternetService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.telecom_service_provisioning.model.TvService;

import java.util.List;

@Repository
public interface TvServiceRepository extends JpaRepository<TvService, Integer> {

    List<TvService> findByActiveTrue();
} 
