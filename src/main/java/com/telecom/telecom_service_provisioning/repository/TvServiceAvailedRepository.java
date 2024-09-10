package com.telecom.telecom_service_provisioning.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.model.compositekey_models.TvServicesAvailedId;

/**
 * TvServiceAvailedRepository
 */
@Repository
public interface TvServiceAvailedRepository extends JpaRepository<TvServiceAvailed, TvServicesAvailedId> {
    
    java.util.List<TvServiceAvailed> findByUserIdAndActiveTrue(Integer userId);

    List<TvServiceAvailed> findByEndDate(LocalDate todayDate);

    List<TvServiceAvailed> findByUserId(Integer userId);

     @Query(value = "SELECT ServiceID, COUNT(ServiceID) AS count FROM Tv_Services_Availed GROUP BY ServiceID ORDER BY count LIMIT 5", nativeQuery = true)
    List<Object[]> findServiceIdAndCount();

}