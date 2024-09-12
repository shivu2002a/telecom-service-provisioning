package com.telecom.telecom_service_provisioning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.telecom_service_provisioning.dto.UserDetailsDto;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.EmailAlreadyTakenException;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.service.implementations.AuthenticationServiceImpl;

@RestController
@CrossOrigin
public class AuthController {
    
    //Sign up
    //Login
    //Details of a user
    
    @Autowired
    public PasswordEncoder passwordEncoder;
    

    @Autowired
    public AuthenticationServiceImpl authService;
    
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) throws EmailAlreadyTakenException {
        authService.signup(user);
        return new ResponseEntity<String>("User succefully created", HttpStatus.CREATED);
    }
    

    @GetMapping("/checkLoggedInUser")
    public ResponseEntity<UserDetailsDto> getUserDetails() {
        UserDetailsDto dto = new UserDetailsDto();
        User user = authService.getCurrentUserDetails();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setUserRole(user.getRole());
        dto.setPhonenumber(user.getPhonenumber());
        dto.setAddress(user.getAddress());
        return new ResponseEntity<UserDetailsDto>(dto, HttpStatus.OK);
    }

    @GetMapping("/userdetails")
    public ResponseEntity<UserDetailsDto> getUserDetailsById(@RequestParam Integer userId) {
        return new ResponseEntity<UserDetailsDto>(authService.getUserDetailsByUserId(userId), HttpStatus.OK);
    }
    
    @GetMapping("/home")
    public String home() {
        return "successfully logged in !!";
    }
}
