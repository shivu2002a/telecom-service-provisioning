package com.telecom.telecom_service_provisioning.model;
import java.io.Serializable;

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
@Table(name = "PendingRequests")
public class PendingRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestID")
    private Integer requestId;

    @Column(name = "ServiceID")
    private Integer serviceId;

    @Column(name = "userID")
    private Integer userId;

    @Column(name = "serviceType", length = 20)
    private String serviceType;

    @Column(name = "requestStatus", length = 15)
    private String requestStatus;

    @Column(name = "remarks", length = 100)
    private String remarks;

    @Column(name = "active")
    private Boolean active;

}
