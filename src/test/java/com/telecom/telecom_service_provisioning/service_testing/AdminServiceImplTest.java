package com.telecom.telecom_service_provisioning.service_testing;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.telecom.telecom_service_provisioning.dto.FeedbackDto;
import com.telecom.telecom_service_provisioning.model.InternetServiceFeedback;
import com.telecom.telecom_service_provisioning.model.TvServiceFeedback;
import com.telecom.telecom_service_provisioning.service.implementations.AdminServiceImpl;
import com.telecom.telecom_service_provisioning.service.implementations.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.telecom.telecom_service_provisioning.dto.MostAvailedServicesDto;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.repository.InternetServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.InternetServiceRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceRepository;
import com.telecom.telecom_service_provisioning.dto.FeedbackDto;

@SpringBootTest
class AdminServiceImplTest {

    @Mock
    private InternetServiceRepository internetServiceRepo;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private TvServiceRepository tvServiceRepo;

    @Mock
    private InternetServiceAvailedRepository internetServiceAvailedRepo;

    @Mock
    private TvServiceAvailedRepository tvServiceAvailedRepo;

    @InjectMocks
    private AdminServiceImpl adminService;

    private InternetService internetService;
    private TvService tvService;
    private FeedbackDto feedbackDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        internetService = new InternetService();
        internetService.setServiceId(1);
        internetService.setServiceName("Fiber 100Mbps");
        internetService.setActive(true);

        tvService = new TvService();
        tvService.setServiceId(1);
        tvService.setServiceName("Basic TV Package");
        tvService.setActive(true);


        // Mocking feedback data
        InternetServiceFeedback internetFeedback1 = new InternetServiceFeedback();
        internetFeedback1.setFeedback("Great internet service!");
        internetFeedback1.setUsername("user1");

        TvServiceFeedback tvFeedback1 = new TvServiceFeedback();
        tvFeedback1.setFeedback("Great TV service!");
        tvFeedback1.setUsername("user2");

        feedbackDto= new FeedbackDto();
        feedbackDto.setInternetServiceFeedbacks(Arrays.asList(internetFeedback1));
        feedbackDto.setTvServiceFeedbacks(Arrays.asList(tvFeedback1));
    }

    @Test
    void testCreateInternetService() {
        when(internetServiceRepo.save(internetService)).thenReturn(internetService);

        InternetService createdService = adminService.createInternetService(internetService);

        assertNotNull(createdService);
        assertTrue(createdService.getActive());
        assertEquals("Fiber 100Mbps", createdService.getServiceName());
        verify(internetServiceRepo, times(1)).save(internetService);
    }

    @Test
    void testGetAllInternetService() {
        List<InternetService> services = Arrays.asList(internetService);
        when(internetServiceRepo.findAll()).thenReturn(services);

        List<InternetService> result = adminService.getAllInternetService();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(internetServiceRepo, times(1)).findAll();
    }

    @Test
    void testTerminateInternetServiceSuccess() throws ResourceNotFoundException {
        when(internetServiceRepo.findById(1)).thenReturn(Optional.of(internetService));

        adminService.terminateInternetService(1);

        assertFalse(internetService.getActive());
        verify(internetServiceRepo, times(1)).save(internetService);
    }

    @Test
    void testTerminateInternetServiceNotFound() {
        when(internetServiceRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            adminService.terminateInternetService(1);
        });

        verify(internetServiceRepo, times(0)).save(any());
    }

    @Test
    void testUpdateInternetService() {
        InternetService updatedService = new InternetService();
        updatedService.setServiceId(1);
        updatedService.setServiceName("Fiber 200Mbps");

        when(internetServiceRepo.findById(1)).thenReturn(Optional.of(internetService));
        when(internetServiceRepo.save(any(InternetService.class))).thenReturn(updatedService);

        InternetService result = adminService.updateInternetService(updatedService);

        assertNotNull(result);
        assertEquals("Fiber 200Mbps", result.getServiceName());
        verify(internetServiceRepo, times(2)).save(any(InternetService.class)); // 1 for deactivating old service, 1 for saving updated
    }

    @Test
    void testCreateTvService() {
        when(tvServiceRepo.save(tvService)).thenReturn(tvService);

        TvService createdService = adminService.createTvService(tvService);

        assertNotNull(createdService);
        assertTrue(createdService.getActive());
        assertEquals("Basic TV Package", createdService.getServiceName());
        verify(tvServiceRepo, times(1)).save(tvService);
    }

    @Test
    void testGetAllTvServices() {
        List<TvService> services = Arrays.asList(tvService);
        when(tvServiceRepo.findAll()).thenReturn(services);

        List<TvService> result = adminService.getAllTvServices();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tvServiceRepo, times(1)).findAll();
    }

    @Test
    void testTerminateTvServiceSuccess() throws ResourceNotFoundException {
        when(tvServiceRepo.findById(1)).thenReturn(Optional.of(tvService));

        adminService.terminateTvService(1);

        assertFalse(tvService.getActive());
        verify(tvServiceRepo, times(1)).save(tvService);
    }

    @Test
    void testTerminateTvServiceNotFound() {
        when(tvServiceRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            adminService.terminateTvService(1);
        });

        verify(tvServiceRepo, times(0)).save(any());
    }

    @Test
    void testGetMostAvailedInternetService() {
        List<Object[]> availedServices = new ArrayList<>();
        availedServices.add(new Object[]{1, 50});

        when(internetServiceAvailedRepo.findServiceIdAndCount()).thenReturn(availedServices);
        when(internetServiceRepo.findById(1)).thenReturn(Optional.of(internetService));

        List<MostAvailedServicesDto> result = adminService.getMostAvailedInternetService();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(50, result.get(0).getSubscribedCount());
    }

    @Test
    void testGetMostAvailedTvService() {
        List<Object[]> availedServices = new ArrayList<>();
        availedServices.add(new Object[]{1, 30});

        when(tvServiceAvailedRepo.findServiceIdAndCount()).thenReturn(availedServices);
        when(tvServiceRepo.findById(1)).thenReturn(Optional.of(tvService));

        List<MostAvailedServicesDto> result = adminService.getMostAvailedTvService();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(30, result.get(0).getSubscribedCount());
    }


    @Test
    void testGetAllFeedbacks() {
        // Mocking the behavior of feedbackService.getAllFeedbacks()
        when(feedbackService.getAllFeedbacks()).thenReturn(feedbackDto);

        // Calling the service method
        FeedbackDto result = adminService.getAllFeedbacks();

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.getInternetServiceFeedbacks().size());
        assertEquals(1, result.getTvServiceFeedbacks().size());

        // Checking specific feedback content
        assertEquals("Great internet service!", result.getInternetServiceFeedbacks().get(0).getFeedback());
        assertEquals("user1", result.getInternetServiceFeedbacks().get(0).getUsername());
        assertEquals("Great TV service!", result.getTvServiceFeedbacks().get(0).getFeedback());
        assertEquals("user2", result.getTvServiceFeedbacks().get(0).getUsername());

        // Verify that feedbackService.getAllFeedbacks() was called once
        verify(feedbackService, times(1)).getAllFeedbacks();
    }



}

