package com.telecom.telecom_service_provisioning.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TvServices")
public class TvService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    @Column(name = "ServiceName", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "Description")
    private String description;

    @Column(name = "benefits")
    private String benefits;

    @Column(name = "criteria")
    private String criteria;

    @Column(name = "active", columnDefinition = "tinyint default 1")
    private Boolean active;

    @Column(name = "MonthlyCost")
    private Double monthlyCost;
}
