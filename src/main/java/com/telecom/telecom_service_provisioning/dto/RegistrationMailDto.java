package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;

/**
 * RegistrationMailDto
 */
@Data
public class RegistrationMailDto {

    private String email, address;
    private String username, subject;

}