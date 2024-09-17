package com.telecom.telecom_service_provisioning.service_testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.telecom.telecom_service_provisioning.service.implementations.InternetServiceManager;
import com.telecom.telecom_service_provisioning.service.implementations.PendingRequestServiceImpl;
import com.telecom.telecom_service_provisioning.service.implementations.TvServiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;
import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.repository.PendingRequestRepository;

class PendingRequestServiceImplTest {

    @Mock
    private PendingRequestRepository pendingRequestRepo;

    @Mock
    private InternetServiceManager internetServiceManager;

    @Mock
    private TvServiceManager tvServiceManager;

    @InjectMocks
    private PendingRequestServiceImpl pendingRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePendingRequest() {
        // Arrange
        PendingRequest pendingRequest = new PendingRequest();
        pendingRequest.setRequestId(1);
        pendingRequest.setUserId(1);
        pendingRequest.setServiceType(PendingRequestServiceType.INTERNET_SERVICE);
        pendingRequest.setRequestStatus(PendingRequestStatus.REQUESTED);

        // Act
        pendingRequestService.createPendingRequest(pendingRequest);

        // Assert
        verify(pendingRequestRepo, times(1)).save(pendingRequest);
    }

    @Test
    void testGetAllPendingRequest() {
        // Arrange
        PendingRequest pendingRequest1 = new PendingRequest();
        PendingRequest pendingRequest2 = new PendingRequest();
        when(pendingRequestRepo.findAll()).thenReturn(Arrays.asList(pendingRequest1, pendingRequest2));

        // Act
        List<PendingRequest> pendingRequests = pendingRequestService.getAllPendingRequest();

        // Assert
        assertEquals(2, pendingRequests.size());
        verify(pendingRequestRepo, times(1)).findAll();
    }

    @Test
    void testUpdatePendingRequest_Approved_InternetService() throws Exception {
        // Arrange
        PendingRequest pendingRequest = new PendingRequest();
        pendingRequest.setRequestId(1);
        pendingRequest.setUserId(1);
        pendingRequest.setServiceId(1);
        pendingRequest.setServiceType(PendingRequestServiceType.INTERNET_SERVICE);
        pendingRequest.setRequestStatus(PendingRequestStatus.APPROVED);
        pendingRequest.setRemarks("Approved");

        when(pendingRequestRepo.findById(1)).thenReturn(Optional.of(pendingRequest));

        // Act
        PendingRequest result = pendingRequestService.updatePendingRequest(pendingRequest);

        // Assert
        assertNotNull(result);
        assertEquals(PendingRequestStatus.APPROVED, result.getRequestStatus());
        verify(pendingRequestRepo, times(1)).save(any(PendingRequest.class));
        verify(internetServiceManager, times(1)).availInternetService(1, 1);
    }

    @Test
    void testUpdatePendingRequest_Approved_TvService() throws Exception {
        // Arrange
        PendingRequest pendingRequest = new PendingRequest();
        pendingRequest.setRequestId(2);
        pendingRequest.setUserId(1);
        pendingRequest.setServiceId(2);
        pendingRequest.setServiceType(PendingRequestServiceType.TV_SERVICE);
        pendingRequest.setRequestStatus(PendingRequestStatus.APPROVED);
        pendingRequest.setRemarks("Approved");

        when(pendingRequestRepo.findById(2)).thenReturn(Optional.of(pendingRequest));

        // Act
        PendingRequest result = pendingRequestService.updatePendingRequest(pendingRequest);

        // Assert
        assertNotNull(result);
        assertEquals(PendingRequestStatus.APPROVED, result.getRequestStatus());
        verify(pendingRequestRepo, times(1)).save(any(PendingRequest.class));
        verify(tvServiceManager, times(1)).availTvService(1, 2);
    }

    @Test
    void testUpdatePendingRequest_Reject() throws Exception {
        // Arrange
        PendingRequest pendingRequest = new PendingRequest();
        pendingRequest.setRequestId(3);
        pendingRequest.setUserId(1);
        pendingRequest.setServiceId(1);
        pendingRequest.setServiceType(PendingRequestServiceType.INTERNET_SERVICE);
        pendingRequest.setRequestStatus(PendingRequestStatus.DISAPPROVED);
        pendingRequest.setRemarks("Rejected");

        when(pendingRequestRepo.findById(3)).thenReturn(Optional.of(pendingRequest));

        // Act
        PendingRequest result = pendingRequestService.updatePendingRequest(pendingRequest);

        // Assert
        assertNotNull(result);
        assertEquals(PendingRequestStatus.DISAPPROVED, result.getRequestStatus());
        verify(pendingRequestRepo, times(1)).save(any(PendingRequest.class));
        verify(internetServiceManager, times(0)).availInternetService(anyInt(), anyInt());
        verify(tvServiceManager, times(0)).availTvService(anyInt(), anyInt());
    }

    @Test
    void testUpdatePendingRequest_NotFound() {
        // Arrange
        PendingRequest pendingRequest = new PendingRequest();
        pendingRequest.setRequestId(4);

        when(pendingRequestRepo.findById(4)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pendingRequestService.updatePendingRequest(pendingRequest);
        });

        verify(pendingRequestRepo, times(0)).save(any(PendingRequest.class));
    }
}

