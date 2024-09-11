package com.telecom.telecom_service_provisioning.model;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.telecom.telecom_service_provisioning.model.compositekey_models.InternetServicesAvailedId;

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
public class InternetServiceAvailed {

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
    // @JsonProperty()
    // private User user;

    @ManyToOne
    @JoinColumn(name = "ServiceID", insertable = false, updatable = false)
    private InternetService internetService;

    @Column(name = "active", columnDefinition = "tinyint default 1")
    private Boolean active;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}

