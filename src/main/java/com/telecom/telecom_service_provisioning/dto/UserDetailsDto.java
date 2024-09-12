package com.telecom.telecom_service_provisioning.dto;

import lombok.Data;

@Data
public class UserDetailsDto {
    private Integer userId;
    private String username;
    private String email;
    private String userRole;
    private String phonenumber;
    private String address;
}
