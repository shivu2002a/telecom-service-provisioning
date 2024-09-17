package com.telecom.telecom_service_provisioning.controller_testing;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.telecom_service_provisioning.controller.AdminController;
import com.telecom.telecom_service_provisioning.dto.FeedbackDto;
import com.telecom.telecom_service_provisioning.dto.MostAvailedServicesDto;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.PendingRequest;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.service.implementations.AdminServiceImpl;
import com.telecom.telecom_service_provisioning.service.implementations.PendingRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import com.telecom.telecom_service_provisioning.constant.PendingRequestStatus;
import com.telecom.telecom_service_provisioning.constant.PendingRequestServiceType;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private AdminServiceImpl adminService;

    @Mock
    private PendingRequestServiceImpl pendingRequestService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        objectMapper = new ObjectMapper();
    }



    // Test POST for InternetService
    @Test
    public void testAddInternetService() throws Exception {
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setServiceName("High-Speed Internet");
        service.setDescription("Offers high-speed internet connectivity.");
        service.setServiceType("Fiber");
        service.setServiceDownloadSpeed(100);
        service.setServiceUploadSpeed(50);
        service.setBenefits("High speed and reliability.");
        service.setCriteria("Available in selected areas.");
        service.setActive(true);
        service.setCost(29.99);

        when(adminService.createInternetService(any(InternetService.class))).thenReturn(service);

        MvcResult result = mockMvc.perform(post("/admin/api/internet-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(service)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(service)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    // Test GET all InternetServices
    @Test
    public void testGetAllInternetServices() throws Exception {
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setServiceName("High-Speed Internet");
        service.setDescription("Offers high-speed internet connectivity.");
        service.setServiceType("Fiber");
        service.setServiceDownloadSpeed(100);
        service.setServiceUploadSpeed(50);
        service.setBenefits("High speed and reliability.");
        service.setCriteria("Available in selected areas.");
        service.setActive(true);
        service.setCost(29.99);

        when(adminService.getAllInternetService()).thenReturn(Collections.singletonList(service));

        MvcResult result = mockMvc.perform(get("/admin/api/internet-service"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(service))))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    // Test PATCH for InternetService
    @Test
    public void testUpdateInternetService() throws Exception {
        InternetService updates = new InternetService();
        updates.setServiceId(1);
        updates.setServiceName("Updated High-Speed Internet");
        updates.setDescription("Updated description for high-speed internet.");
        updates.setServiceType("Fiber");
        updates.setServiceDownloadSpeed(200);
        updates.setServiceUploadSpeed(100);
        updates.setBenefits("Updated benefits description.");
        updates.setCriteria("Updated criteria.");
        updates.setActive(true);
        updates.setCost(39.99);

        when(adminService.updateInternetService(any(InternetService.class))).thenReturn(updates);

        MvcResult result = mockMvc.perform(patch("/admin/api/internet-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(updates)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    // Test DELETE for InternetService
    @Test
    public void testTerminateInternetService() throws Exception {
        doNothing().when(adminService).terminateInternetService(anyInt());

        MvcResult result = mockMvc.perform(delete("/admin/api/internet-service")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Terminated Successfully"))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    // Test POST for TvService
    @Test
    public void testAddTvService() throws Exception {
        TvService service = new TvService();
        service.setServiceId(1);
        service.setServiceName("Premium TV Package");
        service.setDescription("Includes all major channels and premium content.");
        service.setBenefits("High-definition channels and exclusive content.");
        service.setCriteria("Available in all major cities.");
        service.setServiceType("Cable");
        service.setActive(true);
        service.setCost(49.99);

        when(adminService.createTvService(any(TvService.class))).thenReturn(service);

        MvcResult result = mockMvc.perform(post("/admin/api/tv-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(service)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(service)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    // Test GET all TvServices
    @Test
    public void testGetAllTvServices() throws Exception {
        TvService service = new TvService();
        service.setServiceId(1);
        service.setServiceName("Premium TV Package");
        service.setDescription("Includes all major channels and premium content.");
        service.setBenefits("High-definition channels and exclusive content.");
        service.setCriteria("Available in all major cities.");
        service.setServiceType("Cable");
        service.setActive(true);
        service.setCost(49.99);

        when(adminService.getAllTvServices()).thenReturn(Collections.singletonList(service));

        MvcResult result = mockMvc.perform(get("/admin/api/tv-service"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(service))))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    // Test PATCH for TvService
    @Test
    public void testUpdateTvService() throws Exception {
        TvService updates = new TvService();
        updates.setServiceId(1);
        updates.setServiceName("Updated Premium TV Package");
        updates.setDescription("Updated package with additional channels.");
        updates.setBenefits("Additional channels and improved content.");
        updates.setCriteria("Expanded availability.");
        updates.setServiceType("Cable");
        updates.setActive(true);
        updates.setCost(59.99);

        when(adminService.updateTvService(any(TvService.class))).thenReturn(updates);

        MvcResult result = mockMvc.perform(patch("/admin/api/tv-service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(updates)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    // Test DELETE for TvService
    @Test
    public void testTerminateTvService() throws Exception {
        doNothing().when(adminService).terminateTvService(anyInt());

        MvcResult result = mockMvc.perform(delete("/admin/api/tv-service")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Terminated Successfully"))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

     //Test PATCH for PendingRequest
    @Test
    public void testUpdatePendingRequest() throws Exception {
        PendingRequest request = new PendingRequest();
        request.setRequestId(1);
        request.setServiceId(100);
        request.setUserId(200);
        request.setServiceType(PendingRequestServiceType.INTERNET_SERVICE);
        request.setRequestStatus(PendingRequestStatus.REQUESTED);
        request.setRemarks("Request needs approval");
        request.setActive(true);

        when(pendingRequestService.updatePendingRequest(any(PendingRequest.class))).thenReturn(request);

        MvcResult result = mockMvc.perform(patch("/admin/api/approval-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(request)))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    // Test GET all PendingRequests
    @Test
    public void testGetAllPendingRequests() throws Exception {
        PendingRequest request = new PendingRequest();
        request.setRequestId(1);
        request.setServiceId(100);
        request.setUserId(200);
        request.setServiceType(PendingRequestServiceType.INTERNET_SERVICE);
        request.setRequestStatus(PendingRequestStatus.REQUESTED);
        request.setRemarks("Request needs approval");
        request.setActive(true);

        when(pendingRequestService.getAllPendingRequest()).thenReturn(Collections.singletonList(request));

        MvcResult result = mockMvc.perform(get("/admin/api/approval-requests"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(request))))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    void testMostAvailedInternetServices() {
        // Arrange
        List<MostAvailedServicesDto> mostAvailedServices = List.of(new MostAvailedServicesDto());
        when(adminService.getMostAvailedInternetService()).thenReturn(mostAvailedServices);

        // Act
        ResponseEntity<List<MostAvailedServicesDto>> response = adminController.mostAvailedInternetServices();

        // Assert
        verify(adminService).getMostAvailedInternetService();
        assertThat(response.getBody()).isEqualTo(mostAvailedServices);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    void testMostAvailedTvServices() {
        // Arrange
        List<MostAvailedServicesDto> mostAvailedServices = List.of(new MostAvailedServicesDto());
        when(adminService.getMostAvailedTvService()).thenReturn(mostAvailedServices);

        // Act
        ResponseEntity<List<MostAvailedServicesDto>> response = adminController.mostAvailedTvServices();

        // Assert
        verify(adminService).getMostAvailedTvService();
        assertThat(response.getBody()).isEqualTo(mostAvailedServices);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void testGetFeedbacks() {
        // Arrange
        FeedbackDto feedbackDto = new FeedbackDto();
        when(adminService.getAllFeedbacks()).thenReturn(feedbackDto);
        // Act
        ResponseEntity<FeedbackDto> response = adminController.getFeedbacks();
        // Assert
        verify(adminService).getAllFeedbacks();
        assertThat(response.getBody()).isEqualTo(feedbackDto);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }



}
