package com.telecom.telecom_service_provisioning.model;

import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "internet_service_feedback")
public class InternetServiceFeedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @Column(name = "feedback")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "Service_ID")
    private InternetService internetService;

    @Column(name = "username")
    private String username;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
