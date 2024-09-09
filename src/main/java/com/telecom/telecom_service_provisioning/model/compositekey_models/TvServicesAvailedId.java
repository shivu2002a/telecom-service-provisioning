package com.telecom.telecom_service_provisioning.model.compositekey_models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import lombok.Data;

@Data
public class TvServicesAvailedId implements Serializable {

    private Integer userId;
    private Integer serviceId;
    private LocalDate startDate;

    // Default constructor
    public TvServicesAvailedId() {}

    // Parameterized constructor
    public TvServicesAvailedId(Integer userId, Integer serviceId, LocalDate startDate) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TvServicesAvailedId that = (TvServicesAvailedId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(serviceId, that.serviceId) &&
               Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, serviceId, startDate);
    }
}

