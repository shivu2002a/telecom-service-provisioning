package com.telecom.telecom_service_provisioning.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "InternetServices")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class InternetService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    @Column(name = "serviceName", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "description")
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

    @Column(name = "active")
    private Boolean active;

    @Column(name = "monthlyCost", precision = 10)
    private Double monthlyCost;

}
