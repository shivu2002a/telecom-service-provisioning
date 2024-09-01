package com.telecom.telecom_service_provisioning.model;
import java.io.Serializable;

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

    @Column(name = "serviceType", length = 20)
    private String serviceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "requestStatus", length = 50)
    private PendingRequestStatus requestStatus;

    @Column(name = "remarks", length = 100)
    private String remarks;

    @Column(name = "active")
    private Boolean active;
}
