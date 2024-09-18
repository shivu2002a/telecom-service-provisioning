package com.telecom.telecom_service_provisioning.service_testing;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.model.compositekey_models.TvServicesAvailedId;
import com.telecom.telecom_service_provisioning.repository.TvServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.service.implementations.AvailedTvServiceManager;
import com.telecom.telecom_service_provisioning.service.implementations.AuthenticationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AvailedTvServiceManagerTest {

    @Mock
    private AuthenticationServiceImpl authService;

    @Mock
    private TvServiceAvailedRepository availedTvServiceRepo;

    @InjectMocks
    private AvailedTvServiceManager availedTvServiceManager;

    private TvServiceAvailed tvServiceAvailed;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock user
        user = new User();
        user.setUserId(1);

        // Mock TvServiceAvailed
        tvServiceAvailed = new TvServiceAvailed();
        tvServiceAvailed.setUserId(1);
        tvServiceAvailed.setServiceId(1);
        tvServiceAvailed.setStartDate(LocalDate.of(2023, 1, 1));
        tvServiceAvailed.setActive(true);
    }

    @Test
    void testDeactivateService_Success() throws Exception {
        // Arrange
        TvServicesAvailedId tvServicesAvailedId = new TvServicesAvailedId(1, 1, LocalDate.of(2023, 1, 1));

        when(authService.getCurrentUserDetails()).thenReturn(user);
        when(availedTvServiceRepo.findById(tvServicesAvailedId)).thenReturn(Optional.of(tvServiceAvailed));

        // Act
        availedTvServiceManager.deactivateService(1, LocalDate.of(2023, 1, 1));

        // Assert
        assertFalse(tvServiceAvailed.getActive());
        assertEquals(LocalDate.now(), tvServiceAvailed.getEndDate());
        verify(availedTvServiceRepo, times(1)).save(tvServiceAvailed);
    }

    @Test
    void testDeactivateService_ResourceNotFoundException() {
        // Arrange
        TvServicesAvailedId tvServicesAvailedId = new TvServicesAvailedId(1, 1, LocalDate.of(2023, 1, 1));

        when(authService.getCurrentUserDetails()).thenReturn(user);
        when(availedTvServiceRepo.findById(tvServicesAvailedId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            availedTvServiceManager.deactivateService(1, LocalDate.of(2023, 1, 1));
        });

        verify(availedTvServiceRepo, never()).save(any(TvServiceAvailed.class));
    }

    @Test
    void testActivateServiceInQueue_NoServices() {
        // Arrange
        when(availedTvServiceRepo.findByUserIdAndActiveTrue(1)).thenReturn(new ArrayList<>());

        // Act
        availedTvServiceManager.activateServiceInQueue(1);

        // Assert
        verify(availedTvServiceRepo, never()).save(any(TvServiceAvailed.class));
    }

    @Test
    void testActivateServiceInQueue_ServiceActivated() {
        // Arrange
        TvServiceAvailed currentService = new TvServiceAvailed();
        currentService.setUserId(1);
        currentService.setServiceId(1);
        currentService.setStartDate(LocalDate.now().plusDays(1)); // Service queued for tomorrow

        // Mocking the TvService object with validity
        TvService tvService = mock(TvService.class);
        when(tvService.getValidity()).thenReturn(30);  // Assume the service has a validity of 30 days

        currentService.setTvService(tvService);  // Set the mocked TvService

        List<TvServiceAvailed> services = new ArrayList<>();
        services.add(currentService);

        when(availedTvServiceRepo.findByUserIdAndActiveTrue(1)).thenReturn(services);

        // Act
        availedTvServiceManager.activateServiceInQueue(1);

        // Assert
        verify(availedTvServiceRepo, times(1)).deleteById(any(TvServicesAvailedId.class));
        verify(availedTvServiceRepo, times(1)).save(any(TvServiceAvailed.class));
    }

}

