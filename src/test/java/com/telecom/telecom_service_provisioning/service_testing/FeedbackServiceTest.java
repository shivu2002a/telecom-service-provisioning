package com.telecom.telecom_service_provisioning.service_testing;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.telecom.telecom_service_provisioning.dto.FeedbackDto;
import com.telecom.telecom_service_provisioning.model.*;
import com.telecom.telecom_service_provisioning.repository.InternetServiceFeedbackRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceFeedbackRepository;
import com.telecom.telecom_service_provisioning.service.implementations.AuthenticationServiceImpl;
import com.telecom.telecom_service_provisioning.service.implementations.FeedbackService;
import com.telecom.telecom_service_provisioning.service.implementations.InternetServiceManager;
import com.telecom.telecom_service_provisioning.service.implementations.TvServiceManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class FeedbackServiceTest {

    @Mock
    private TvServiceFeedbackRepository tvServiceFeedbackRepo;

    @Mock
    private InternetServiceFeedbackRepository internetServiceFeedbackRepo;

    @Mock
    private AuthenticationServiceImpl authService;

    @Mock
    private InternetServiceManager internetServiceManager;

    @Mock
    private TvServiceManager tvServiceManager;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Modify the test to mock the User object properly
    @Test
    void testCreateInternetServiceFeedback_Success() throws Exception {
        // Arrange
        InternetService internetService = new InternetService();
        internetService.setServiceId(1);

        // Mocking the User object
        User mockUser = new User();
        mockUser.setUsername("testuser");

        when(authService.getCurrentUserDetails()).thenReturn(mockUser); // Mocking the user returned by authService
        when(internetServiceManager.getInternetServiceDetails(1)).thenReturn(internetService);

        // Act
        feedbackService.createInternetServiceFeedback(1, "Great service!");

        // Assert
        verify(internetServiceFeedbackRepo, times(1)).save(any(InternetServiceFeedback.class));
    }

    @Test
    void testCreateTvServiceFeedback_Success() throws Exception {
        // Arrange
        TvService tvService = new TvService();
        tvService.setServiceId(1);

        // Mocking the User object
        User mockUser = new User();
        mockUser.setUsername("testuser");

        when(authService.getCurrentUserDetails()).thenReturn(mockUser); // Mocking the user returned by authService
        when(tvServiceManager.getTvServiceDetails(1)).thenReturn(tvService);

        // Act
        feedbackService.createTvServiceFeedback(1, "Awesome TV service!");

        // Assert
        verify(tvServiceFeedbackRepo, times(1)).save(any(TvServiceFeedback.class));
    }


    @Test
    void testGetAllFeedbacks() {
        // Arrange
        List<InternetServiceFeedback> internetFeedbacks = new ArrayList<>();
        List<TvServiceFeedback> tvFeedbacks = new ArrayList<>();

        InternetServiceFeedback internetFeedback = new InternetServiceFeedback();
        internetFeedback.setFeedback("Great internet service!");
        internetFeedbacks.add(internetFeedback);

        TvServiceFeedback tvFeedback = new TvServiceFeedback();
        tvFeedback.setFeedback("Excellent TV service!");
        tvFeedbacks.add(tvFeedback);

        when(internetServiceFeedbackRepo.findAll()).thenReturn(internetFeedbacks);
        when(tvServiceFeedbackRepo.findAll()).thenReturn(tvFeedbacks);

        // Act
        FeedbackDto feedbackDto = feedbackService.getAllFeedbacks();

        // Assert
        assertNotNull(feedbackDto);
        assertEquals(1, feedbackDto.getInternetServiceFeedbacks().size());
        assertEquals(1, feedbackDto.getTvServiceFeedbacks().size());
    }
}

