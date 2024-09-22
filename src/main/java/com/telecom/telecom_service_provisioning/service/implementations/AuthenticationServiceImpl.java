package com.telecom.telecom_service_provisioning.service.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.dto.UserDetailsDto;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.EmailAlreadyTakenException;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.repository.UserRepository;
import com.telecom.telecom_service_provisioning.service.Interfaces.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final String ROLE_USER = "ROLE_USER";

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public EmailMiddlewareService emailMiddlewareService;

    public void signup(User user) throws EmailAlreadyTakenException {
        if(userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new EmailAlreadyTakenException("Email or username is already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole(ROLE_USER); 
        }
        userRepository.save(user);
        emailMiddlewareService.sendRegistrationMail(user);
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
    
    public UserDetailsDto getUserDetailsByUserId(Integer userId) {
        Optional<User> optUser = userRepository.findById(userId);
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUsername(optUser.get().getUsername());
        userDetailsDto.setEmail(optUser.get().getEmail());
        userDetailsDto.setPhonenumber(optUser.get().getPhonenumber());
        userDetailsDto.setAddress(optUser.get().getAddress());
        return userDetailsDto;
    }
}
