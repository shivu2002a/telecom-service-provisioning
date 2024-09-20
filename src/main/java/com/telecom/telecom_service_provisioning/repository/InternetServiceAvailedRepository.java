package com.telecom.telecom_service_provisioning.repository;

import org.apache.el.stream.Optional;
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

    java.util.Optional<InternetServiceAvailed> findByUserIdAndServiceId(Integer userId, Integer serviceId);

    @Query("SELECT i FROM InternetServiceAvailed i WHERE i.userId = :userId AND i.serviceId = :serviceId AND i.startDate = :startDate AND i.active = true")
    java.util.Optional<InternetServiceAvailed> findByCompositeKeyAndActiveTrue(Integer userId, Integer serviceId, LocalDate startDate);

    List<InternetServiceAvailed> findByStartDate(LocalDate now);

    List<InternetServiceAvailed> findByUserIdAndActiveFalse(Integer userId);

    



}
