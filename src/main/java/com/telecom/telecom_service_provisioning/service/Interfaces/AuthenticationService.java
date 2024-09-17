package com.telecom.telecom_service_provisioning.service.Interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import com.telecom.telecom_service_provisioning.dto.UserDetailsDto;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.EmailAlreadyTakenException;
import com.telecom.telecom_service_provisioning.model.User;

public interface AuthenticationService {

    public void signup(User user) throws EmailAlreadyTakenException;  
    
    public UserDetails getCurrentLoggedInUserDetails();

    public User getCurrentUserDetails();
    
    public UserDetailsDto getUserDetailsByUserId(Integer userId);
    
}