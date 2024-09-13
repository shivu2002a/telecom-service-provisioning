package com.telecom.telecom_service_provisioning.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "InternetServices")
public class InternetService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    @Column(name = "ServiceName", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "Description")
    private String description;

    @Column(name = "serviceType", length = 50)
    private String serviceType;

    @Column(name = "serviceDownloadSpeed")
    private Integer serviceDownloadSpeed;

    @Column(name = "serviceUploadSpeed")
    private Integer serviceUploadSpeed;

    @Column(name = "benefits")
    private String benefits;

    @Column(name = "criteria")
    private String criteria;

    @Column(name = "active", columnDefinition = "tinyint default 1")
    private Boolean active;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "validity",  nullable = false)
    private Integer validity;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    
}
