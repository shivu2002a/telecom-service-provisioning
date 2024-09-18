package com.telecom.telecom_service_provisioning.service_testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.service.implementations.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.telecom.telecom_service_provisioning.dto.UserDetailsDto;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.EmailAlreadyTakenException;
import com.telecom.telecom_service_provisioning.repository.UserRepository;

class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User user;  // This is the correct field-level user

    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the field-level 'user' object
        user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        // Mock user details
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        // Mock Authentication
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.isAuthenticated()).thenReturn(true);  // Mock it as authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testSignup() throws EmailAlreadyTakenException {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        authenticationService.signup(user);

        verify(userRepository, times(1)).save(user);
        assertEquals("ROLE_USER", user.getRole());
        assertEquals("encodedPassword", user.getPassword());  // Check for the encoded password
    }

    @Test
    void testSignup_EmailAlreadyTakenException() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Exception exception = assertThrows(EmailAlreadyTakenException.class, () -> {
            authenticationService.signup(user);
        });

        assertEquals("Email or username is already taken", exception.getMessage());
    }

    @Test
    void testGetCurrentLoggedInUserDetails() {
        UserDetails result = authenticationService.getCurrentLoggedInUserDetails();

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetCurrentUserDetails() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User result = authenticationService.getCurrentUserDetails();

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testGetUserDetailsByUserId() {
        user.setUserId(1);
        user.setPhonenumber("1234567890");
        user.setAddress("123 Test St");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDetailsDto result = authenticationService.getUserDetailsByUserId(1);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("1234567890", result.getPhonenumber());
        assertEquals("123 Test St", result.getAddress());
    }
}
