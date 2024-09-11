package com.telecom.telecom_service_provisioning.model;

import java.time.LocalDate;
import java.sql.Timestamp;


import org.hibernate.annotations.CreationTimestamp;

import com.telecom.telecom_service_provisioning.model.compositekey_models.TvServicesAvailedId;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TvServicesAvailed")
@IdClass(TvServicesAvailedId.class)
public class TvServiceAvailed {

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

    // @ManyToOne
    // @JoinColumn(name = "UserID", insertable = false, updatable = false)
    // private User user;

    @ManyToOne
    @JoinColumn(name = "ServiceID", insertable = false, updatable = false)
    private TvService tvService;

    @Column(name = "active", columnDefinition = "tinyint default 1")
    private Boolean active;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
