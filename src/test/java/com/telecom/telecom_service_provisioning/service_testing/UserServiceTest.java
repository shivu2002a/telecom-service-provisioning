package com.telecom.telecom_service_provisioning.service_testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.telecom.telecom_service_provisioning.service.implementations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.telecom.telecom_service_provisioning.dto.AvailedServices;
import com.telecom.telecom_service_provisioning.dto.UserDetailsDto;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private AvailedInternetServiceManager availedInternetService;

    @Mock
    private AuthenticationServiceImpl authService;

    @Mock
    private AvailedTvServiceManager availedTvService;

    @Mock
    private PendingRequestRepository pendingRequestRepo;

    @Mock
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllSubscribedServices_WithServices() {
        // Arrange
        Integer userId = 1;
        InternetServiceAvailed internetService = new InternetServiceAvailed();
        TvServiceAvailed tvService = new TvServiceAvailed();

        User mockUser = new User();
        mockUser.setUserId(userId);

        // Stubbing methods
        when(authService.getCurrentUserDetails()).thenReturn(mockUser);
        when(availedInternetService.getActiveSubscribedServices(userId)).thenReturn(Arrays.asList(internetService));
        when(availedTvService.getActiveSubscribedServices(userId)).thenReturn(Arrays.asList(tvService));

        // Act
        AvailedServices result = userService.getAllSubscribedServices();

        // Assert
        assertNotNull(result);
        assertFalse(result.getInternetServicesAvailed().isEmpty());
        assertFalse(result.getTvServicesAvailed().isEmpty());
    }


    @Test
    void testDeactivateInternetService_Success() throws Exception {
        // Arrange
        Integer availedServiceId = 1;
        LocalDate startDate = LocalDate.now();

        // Act
        userService.deactivateInternetService(availedServiceId, startDate);

        // Assert
        verify(availedInternetService, times(1)).deactivateService(availedServiceId, startDate);
    }

    @Test
    void testDeactivateTvService_Success() throws Exception {
        // Arrange
        Integer availedServiceId = 1;
        LocalDate startDate = LocalDate.now();

        // Act
        userService.deactivateTvService(availedServiceId, startDate);

        // Assert
        verify(availedTvService, times(1)).deactivateService(availedServiceId, startDate);
    }

    @Test
    void testGetUserDetails_Success() {
        // Arrange
        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setEmail("test@example.com");
        mockUser.setUsername("testuser");
        mockUser.setRole("USER");
        mockUser.setAddress("123 Test St");
        mockUser.setPhonenumber("1234567890");

        when(authService.getCurrentUserDetails()).thenReturn(mockUser);

        // Act
        UserDetailsDto result = userService.getUserDetails();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("testuser", result.getUsername());
        assertEquals("USER", result.getUserRole());
        assertEquals("123 Test St", result.getAddress());
        assertEquals("1234567890", result.getPhonenumber());
    }

    @Test
    void testGetAllPendingRequests_Success() {
        // Arrange
        Integer userId = 1; // This should match the ID used in the method under test
        PendingRequest pendingRequest = new PendingRequest();

        // Mocking User object to return the specific userId
        User mockUser = new User();
        mockUser.setUserId(userId);

        // Stubbing the methods
        when(authService.getCurrentUserDetails()).thenReturn(mockUser);
        when(pendingRequestRepo.findByUserId(userId)).thenReturn(Arrays.asList(pendingRequest));

        // Act
        List<PendingRequest> result = userService.getAllPendingRequests();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testCreateTvServiceFeedback_Success() throws Exception {
        // Arrange
        Integer availedTvServiceId = 1;
        String feedback = "Great service!";

        // Act
        userService.createTvServiceFeedback(availedTvServiceId, feedback);

        // Assert
        verify(feedbackService, times(1)).createTvServiceFeedback(availedTvServiceId, feedback);
    }

    @Test
    void testCreateInternetServiceFeedback_Success() throws Exception {
        // Arrange
        Integer availedInternetServiceId = 1;
        String feedback = "Great service!";

        // Act
        userService.createInternetServiceFeedback(availedInternetServiceId, feedback);

        // Assert
        verify(feedbackService, times(1)).createInternetServiceFeedback(availedInternetServiceId, feedback);
    }
}

