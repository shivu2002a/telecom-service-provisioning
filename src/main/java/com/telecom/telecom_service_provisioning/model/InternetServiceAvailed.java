package com.telecom.telecom_service_provisioning.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.telecom.telecom_service_provisioning.model.compositekeyModels.InternetServicesAvailedId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "InternetServicesAvailed")
@IdClass(InternetServicesAvailedId.class)
public class InternetServiceAvailed implements Serializable {

    @Id
    @Column(name = "UserID")
    private Integer userId;

    @Id
    @Column(name = "ServiceID")
    private Integer serviceId;

    @Id
    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ServiceID", insertable = false, updatable = false)
    private InternetService internetService;
}

