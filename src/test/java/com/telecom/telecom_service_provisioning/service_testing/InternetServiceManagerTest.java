package com.telecom.telecom_service_provisioning.service_testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.telecom.telecom_service_provisioning.service.implementations.AuthenticationServiceImpl;
import com.telecom.telecom_service_provisioning.service.implementations.InternetServiceManager;
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
import com.telecom.telecom_service_provisioning.model.*;
import com.telecom.telecom_service_provisioning.repository.*;

class InternetServiceManagerTest {

    @Mock
    private AuthenticationServiceImpl authService;

    @Mock
    private InternetServiceRepository internetServiceRepo;

    @Mock
    private PendingRequestRepository pendingRequestRepo;

    @Mock
    private InternetServiceAvailedRepository internetServiceAvailedRepo;

    @InjectMocks
    private InternetServiceManager internetServiceManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllInternetService_Success() {
        // Arrange
        InternetService service1 = new InternetService();
        service1.setServiceId(1);
        service1.setActive(true);

        InternetService service2 = new InternetService();
        service2.setServiceId(2);
        service2.setActive(true);

        when(internetServiceRepo.findByActiveTrue()).thenReturn(Arrays.asList(service1, service2));

        // Act
        List<InternetService> result = internetServiceManager.getAllInternetService();

        // Assert
        assertEquals(2, result.size());
        verify(internetServiceRepo, times(1)).findByActiveTrue();
    }

    @Test
    void testGetInternetServiceDetails_Success() throws ResourceNotFoundException {
        // Arrange
        InternetService service = new InternetService();
        service.setServiceId(1);
        when(internetServiceRepo.findById(1)).thenReturn(Optional.of(service));

        // Act
        InternetService result = internetServiceManager.getInternetServiceDetails(1);

        // Assert
        assertEquals(1, result.getServiceId());
        verify(internetServiceRepo, times(1)).findById(1);
    }

    @Test
    void testGetInternetServiceDetails_ThrowsResourceNotFoundException() {
        // Arrange
        when(internetServiceRepo.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            internetServiceManager.getInternetServiceDetails(1);
        });
        verify(internetServiceRepo, times(1)).findById(1);
    }

    @Test
    void testSubscribeToService_Success() throws Exception {
        // Arrange
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setCriteria(null);  // Ensure this is null for direct subscription
        service.setValidity(30);    // Set the validity for the service
        User mockUser = new User();
        mockUser.setUserId(1);

        when(internetServiceRepo.findById(1)).thenReturn(Optional.of(service));
        when(authService.getCurrentUserDetails()).thenReturn(mockUser);
        when(internetServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList());

        // Act
        boolean result = internetServiceManager.subscribeToService(1);

        // Assert
        assertTrue(result);
        verify(internetServiceAvailedRepo, times(1)).save(any(InternetServiceAvailed.class));
    }

    @Test
    void testSubscribeToService_WithPendingRequest() throws Exception {
        // Arrange
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setCriteria("Criteria");
        User mockUser = new User();
        mockUser.setUserId(1);

        when(internetServiceRepo.findById(1)).thenReturn(Optional.of(service));
        when(authService.getCurrentUserDetails()).thenReturn(mockUser);

        // Act
        boolean result = internetServiceManager.subscribeToService(1);

        // Assert
        assertFalse(result);
        verify(pendingRequestRepo, times(1)).save(any(PendingRequest.class));
    }

    @Test
    void testSubscribeToService_ThrowsMaxServicesAlreadyAvailedException() throws Exception {
        // Arrange
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setCriteria(null);
        User mockUser = new User();
        mockUser.setUserId(1);

        when(internetServiceRepo.findById(1)).thenReturn(Optional.of(service));
        when(authService.getCurrentUserDetails()).thenReturn(mockUser);
        when(internetServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList(new InternetServiceAvailed(), new InternetServiceAvailed()));

        //
        // Act & Assert
        assertThrows(MaxServicesAlreadyAvailedException.class, () -> {
            internetServiceManager.subscribeToService(1);
        });
        verify(internetServiceAvailedRepo, times(1)).findByUserIdAndActiveTrue(1);
        verify(internetServiceAvailedRepo, times(0)).save(any(InternetServiceAvailed.class));
    }



    @Test
    void testAvailInternetService_Success_NewService() throws Exception {
        // Arrange
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setValidity(30);
        User mockUser = new User();
        mockUser.setUserId(1);

        when(internetServiceRepo.findById(1)).thenReturn(Optional.of(service));
        when(internetServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList());
        when(pendingRequestRepo.findByUserIdAndServiceTypeAndActiveTrue(1, PendingRequestServiceType.INTERNET_SERVICE)).thenReturn(Arrays.asList());

        // Act
        internetServiceManager.availInternetService(1, 1);

        // Assert
        verify(internetServiceAvailedRepo, times(1)).save(any(InternetServiceAvailed.class));
    }

    @Test
    void testAvailInternetService_ThrowsMaxServicesAlreadyAvailedException() throws Exception {
        // Arrange
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setValidity(30);
        User mockUser = new User();
        mockUser.setUserId(1);

        when(internetServiceRepo.findById(1)).thenReturn(Optional.of(service));
        when(internetServiceAvailedRepo.findByUserIdAndActiveTrue(1)).thenReturn(Arrays.asList(new InternetServiceAvailed()));
        when(pendingRequestRepo.findByUserIdAndServiceTypeAndActiveTrue(1, PendingRequestServiceType.INTERNET_SERVICE))
                .thenReturn(Arrays.asList(new PendingRequest()));

        // Act & Assert
        assertThrows(MaxServicesAlreadyAvailedException.class, () -> {
            internetServiceManager.availInternetService(1, 1);
        });
    }
}
