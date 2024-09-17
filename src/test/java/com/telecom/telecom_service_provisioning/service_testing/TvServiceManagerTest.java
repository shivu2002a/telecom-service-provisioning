package com.telecom.telecom_service_provisioning.service_testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import com.telecom.telecom_service_provisioning.service.implementations.AuthenticationServiceImpl;
import com.telecom.telecom_service_provisioning.service.implementations.TvServiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;
import com.telecom.telecom_service_provisioning.dto.ModifySubscription;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.MaxServicesAlreadyAvailedException;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.model.User;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceRepository;

class TvServiceManagerTest {

    @Mock
    private AuthenticationServiceImpl authService;

    @Mock
    private TvServiceRepository tvServiceRepo;

    @Mock
    private PendingRequestRepository pendingRequestRepo;

    @Mock
    private TvServiceAvailedRepository tvServiceAvailedRepo;

    @InjectMocks
    private TvServiceManager tvServiceManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubscribeToTvService_DirectSubscription_Success() throws Exception {
        // Arrange
        TvService tvService = new TvService();
        tvService.setServiceId(1);
        tvService.setCriteria(null); // This allows direct subscription
        tvService.setValidity(30);   // Set validity to a non-null value (e.g., 30 days)

        User mockUser = new User();
        mockUser.setUserId(1);

        when(tvServiceRepo.findById(1)).thenReturn(Optional.of(tvService));
        when(authService.getCurrentUserDetails()).thenReturn(mockUser);
        when(tvServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList());

        // Act
        boolean result = tvServiceManager.subscribeToTvService(1);

        // Assert
        assertTrue(result);
        verify(tvServiceAvailedRepo, times(1)).save(any(TvServiceAvailed.class));
    }


    @Test
    void testSubscribeToTvService_PendingRequest_Created() throws Exception {
        // Arrange
        TvService tvService = new TvService();
        tvService.setServiceId(1);
        tvService.setCriteria("Requires approval");

        User mockUser = new User();
        mockUser.setUserId(1);

        when(tvServiceRepo.findById(1)).thenReturn(Optional.of(tvService));
        when(authService.getCurrentUserDetails()).thenReturn(mockUser);

        // Act
        boolean result = tvServiceManager.subscribeToTvService(1);

        // Assert
        assertFalse(result);
        verify(pendingRequestRepo, times(1)).save(any(PendingRequest.class));
    }

    @Test
    void testSubscribeToTvService_ThrowsMaxServicesAlreadyAvailedException() {
        // Arrange
        TvService tvService = new TvService();
        tvService.setServiceId(1);
        tvService.setCriteria(null);

        User mockUser = new User();
        mockUser.setUserId(1);

        when(tvServiceRepo.findById(1)).thenReturn(Optional.of(tvService));
        when(authService.getCurrentUserDetails()).thenReturn(mockUser);
        when(tvServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList(new TvServiceAvailed(), new TvServiceAvailed()));

        // Act & Assert
        assertThrows(MaxServicesAlreadyAvailedException.class, () -> {
            tvServiceManager.subscribeToTvService(1);
        });
    }

    @Test
    void testAvailTvService_Success() throws Exception {
        // Arrange
        TvService tvService = new TvService();
        tvService.setServiceId(1);
        tvService.setValidity(30);

        User mockUser = new User();
        mockUser.setUserId(1);

        when(tvServiceRepo.findById(1)).thenReturn(Optional.of(tvService));
        when(tvServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList());

        // Act
        tvServiceManager.availTvService(1, 1);

        // Assert
        verify(tvServiceAvailedRepo, times(1)).save(any(TvServiceAvailed.class));
    }

    @Test
    void testAvailTvService_ThrowsMaxServicesAlreadyAvailedException() {
        // Arrange
        User mockUser = new User();
        mockUser.setUserId(1);

        when(tvServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList(new TvServiceAvailed(), new TvServiceAvailed()));
        when(pendingRequestRepo.findByUserIdAndServiceTypeAndActiveTrue(1, PendingRequestServiceType.TV_SERVICE))
                .thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(MaxServicesAlreadyAvailedException.class, () -> {
            tvServiceManager.availTvService(1, 1);
        });
    }




}
