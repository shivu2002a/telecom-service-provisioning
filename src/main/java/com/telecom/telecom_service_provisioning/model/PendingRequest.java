package com.telecom.telecom_service_provisioning.model;
import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;

import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "serviceType", length = 20)
    private PendingRequestServiceType serviceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "requestStatus", length = 50)
    private PendingRequestStatus requestStatus;

    @Column(name = "remarks", length = 100)
    private String remarks;

    @Column(name = "active", columnDefinition = "tinyint default 1")
    private Boolean active;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
