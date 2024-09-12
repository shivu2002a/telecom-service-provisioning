package com.telecom.telecom_service_provisioning.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "tv_service_feedback")
public class TvServiceFeedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @Column(name = "feedback")
    private String feedback;


    @ManyToOne
    @JoinColumn(name = "Service_ID") //insertable = false, updatable = false
    private TvService tvService;

    @Column(name = "username")
    private String username;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    
}
