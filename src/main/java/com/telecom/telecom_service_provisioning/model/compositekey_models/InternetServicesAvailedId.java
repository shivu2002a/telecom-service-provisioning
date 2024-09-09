package com.telecom.telecom_service_provisioning.model.compositekey_models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import lombok.Data;

@Data
public class InternetServicesAvailedId implements Serializable {

    private Integer userId;
    private Integer serviceId;
    private LocalDate startDate;

    // Default constructor
    public InternetServicesAvailedId() {}

    // Parameterized constructor
    public InternetServicesAvailedId(Integer userId, Integer serviceId, LocalDate startDate) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternetServicesAvailedId that = (InternetServicesAvailedId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(serviceId, that.serviceId) &&
               Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, serviceId, startDate);
    }
}

