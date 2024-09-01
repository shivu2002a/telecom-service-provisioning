package com.telecom.telecom_service_provisioning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions.EmailAlreadyTakenException;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.service.AuthorizationService;

@RestController
@CrossOrigin
// @RequestMapping("/auth")
public class AuthController {
    
    //Sign up
    //Login
    //Details of a user
    
    @Autowired
    public PasswordEncoder passwordEncoder;
    

    @Autowired
    public AuthorizationService authService;
    
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) throws EmailAlreadyTakenException {
        authService.signup(user);
        return new ResponseEntity<String>("User succefully created", HttpStatus.CREATED);
    }
    

    @GetMapping("/checkLoggedInUser")
    public String getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                // Use the userDetails object as needed
                System.out.println("Logged in user: " + userDetails.getUsername() + " " + userDetails.getAuthorities());
            } else {
                // Handle other principal types
                System.out.println("Logged in user: " + principal.toString());
            }
        }
        return "Logged in user: ";
    }
    
    @GetMapping("/home")
    public String home() {
        return "successfully logged in !!";
    }
}
