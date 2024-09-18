package com.telecom.telecom_service_provisioning.service_testing;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.model.compositekey_models.InternetServicesAvailedId;
import com.telecom.telecom_service_provisioning.repository.InternetServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.service.implementations.AuthenticationServiceImpl;
import com.telecom.telecom_service_provisioning.service.implementations.AvailedInternetServiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


class AvailedInternetServiceManagerTest {

    @Mock
    private AuthenticationServiceImpl authService;

    @Mock
    private InternetServiceAvailedRepository internetServiceAvailedRepo;

    @InjectMocks
    private AvailedInternetServiceManager availedInternetServiceManager;

    private InternetServiceAvailed internetServiceAvailed;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a sample InternetServiceAvailed object
        internetServiceAvailed = new InternetServiceAvailed();
        internetServiceAvailed.setServiceId(1);
        internetServiceAvailed.setUserId(1);
        internetServiceAvailed.setStartDate(LocalDate.of(2023, 1, 1));
        internetServiceAvailed.setEndDate(LocalDate.of(2023, 12, 31));
        internetServiceAvailed.setActive(true);


        User mockUser = mock(User.class);
        when(mockUser.getUserId()).thenReturn(1);

        // Mock the authentication service to return the mock user
        when(authService.getCurrentUserDetails()).thenReturn(mockUser);
    }

    @Test
    void testGetActiveSubscribedServices() {
        // Arrange
        when(internetServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList(internetServiceAvailed));

        // Act
        List<InternetServiceAvailed> activeServices = availedInternetServiceManager.getActiveSubscribedServices(1);

        // Assert
        assertNotNull(activeServices);
        assertEquals(1, activeServices.size());
        assertEquals(internetServiceAvailed, activeServices.get(0));
    }

    @Test
    void testDeactivateService_Success() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        InternetServicesAvailedId intServiceId = new InternetServicesAvailedId(1, 1, startDate);

        // Mock the InternetServiceAvailed object
        InternetServiceAvailed internetServiceAvailed = new InternetServiceAvailed();
        internetServiceAvailed.setActive(true);
        internetServiceAvailed.setStartDate(startDate);

        // Mock the repository to return the InternetServiceAvailed object
        when(internetServiceAvailedRepo.findById(intServiceId)).thenReturn(Optional.of(internetServiceAvailed));

        // Act
        availedInternetServiceManager.deactivateService(1, startDate);

        // Assert
        assertFalse(internetServiceAvailed.getActive());
        assertEquals(LocalDate.now(), internetServiceAvailed.getEndDate());
        verify(internetServiceAvailedRepo, times(1)).save(internetServiceAvailed);
    }


    @Test
    void testDeactivateService_ServiceNotFound() {
        // Arrange
        InternetServicesAvailedId intServiceId = new InternetServicesAvailedId(1,1,  LocalDate.of(2023, 1, 1));
        when(authService.getCurrentUserDetails().getUserId()).thenReturn(1);
        when(internetServiceAvailedRepo.findById(intServiceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            availedInternetServiceManager.deactivateService(1, LocalDate.of(2023, 1, 1));
        });
    }

    @Test
    void testActivateServiceInQueue_WithActiveServices() {
        // Arrange
        when(internetServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList(internetServiceAvailed));

        // Act
        availedInternetServiceManager.activateServiceInQueue(1);

        // Assert
        // In this case, since the start date is not after the current date, nothing should change.
        verify(internetServiceAvailedRepo, never()).save(any());
    }

    @Test
    void testActivateServiceInQueue_NoActiveServices() {
        // Arrange
        when(internetServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Collections.emptyList());

        // Act
        availedInternetServiceManager.activateServiceInQueue(1);

        // Assert
        // No active services means no activation should occur
        verify(internetServiceAvailedRepo, never()).save(any());
    }

    @Test
    void testFindByEndDate() {
        // Arrange
        LocalDate todayDate = LocalDate.now();
        when(internetServiceAvailedRepo.findByEndDate(todayDate)).thenReturn(Arrays.asList(internetServiceAvailed));

        // Act
        List<InternetServiceAvailed> servicesByEndDate = availedInternetServiceManager.findByEndDate(todayDate);

        // Assert
        assertNotNull(servicesByEndDate);
        assertEquals(1, servicesByEndDate.size());
        assertEquals(internetServiceAvailed, servicesByEndDate.get(0));
    }
}

