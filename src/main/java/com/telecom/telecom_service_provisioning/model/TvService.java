package com.telecom.telecom_service_provisioning.model;

import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
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

    @Column(name = "serviceType", length = 50)
    private String serviceType;

    @Column(name = "active", columnDefinition = "tinyint default 1")
    private Boolean active;

    @Column(name = "Cost", nullable = false)
    private Double cost;

    @Column(name = "validity",  nullable = false)
    private Integer validity;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
