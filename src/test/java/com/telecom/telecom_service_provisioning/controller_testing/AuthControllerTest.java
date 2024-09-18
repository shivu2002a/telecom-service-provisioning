package com.telecom.telecom_service_provisioning.controller_testing;

import com.telecom.telecom_service_provisioning.controller.AuthController;
import com.telecom.telecom_service_provisioning.dto.UserDetailsDto;
import com.telecom.telecom_service_provisioning.service.implementations.AuthenticationServiceImpl;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.EmailAlreadyTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationServiceImpl authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignup_Success() throws EmailAlreadyTakenException {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("ROLE_USER");

        ResponseEntity<String> response = authController.signup(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User succefully created", response.getBody());
    }

    
    @Test
    public void testGetUserDetails_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setRole("ROLE_USER");

        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUsername("testuser");
        userDetailsDto.setEmail("test@example.com");
        userDetailsDto.setUserRole("ROLE_USER");

        when(authService.getCurrentUserDetails()).thenReturn(user);

        ResponseEntity<UserDetailsDto> response = authController.getUserDetails();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDetailsDto, response.getBody());
    }

    @Test
    public void testGetUserDetailsById_Success() {
        Integer userId = 1;
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUsername("testuser");
        userDetailsDto.setEmail("test@example.com");
        userDetailsDto.setUserRole("ROLE_USER");

        when(authService.getUserDetailsByUserId(userId)).thenReturn(userDetailsDto);

        ResponseEntity<UserDetailsDto> response = authController.getUserDetailsById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDetailsDto, response.getBody());
    }

    @Test
    public void testHome_Success() {
        String response = authController.home();

        assertEquals("successfully logged in !!", response);
    }
}

