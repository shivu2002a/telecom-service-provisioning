package com.telecom.telecom_service_provisioning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.compositekey_models.InternetServicesAvailedId;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InternetServiceAvailedRepository extends JpaRepository<InternetServiceAvailed, InternetServicesAvailedId>{
    
    List<InternetServiceAvailed> findByUserIdAndActiveTrue(Integer userId);

    List<InternetServiceAvailed> findByEndDate(LocalDate endDate);

    List<InternetServiceAvailed> findByUserId(Integer userId);

    @Query(value = "SELECT ServiceID, COUNT(ServiceID) AS count FROM Internet_Services_Availed GROUP BY ServiceID ORDER BY count LIMIT 5", nativeQuery = true)
    List<Object[]> findServiceIdAndCount();



}
