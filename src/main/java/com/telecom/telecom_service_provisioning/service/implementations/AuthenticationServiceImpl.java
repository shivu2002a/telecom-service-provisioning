package com.telecom.telecom_service_provisioning.service.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions.EmailAlreadyTakenException;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.repository.UserRepository;

@Service
public class AuthenticationServiceImpl {

    public static final String ROLE_USER = "ROLE_USER";

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public void signup(User user) throws EmailAlreadyTakenException {
        if(userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new EmailAlreadyTakenException("Email or username is already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(ROLE_USER); 
        userRepository.save(user);
    }

    public UserDetails getCurrentLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return userDetails;
            }
        }
        return null;
    }

    public User getCurrentUserDetails() {
        UserDetails userDetails = getCurrentLoggedInUserDetails();
        Optional<User> optUser = userRepository.findByUsername(userDetails.getUsername());
        return optUser.get();
    }    
}
