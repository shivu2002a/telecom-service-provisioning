package com.telecom.telecom_service_provisioning.controller_testing;

import com.telecom.telecom_service_provisioning.controller.UserController;
import com.telecom.telecom_service_provisioning.dto.AvailedServices;
import com.telecom.telecom_service_provisioning.dto.UserDetailsDto;
import com.telecom.telecom_service_provisioning.model.*;
import com.telecom.telecom_service_provisioning.service.implementations.InternetServiceManager;
import com.telecom.telecom_service_provisioning.service.implementations.TvServiceManager;
import com.telecom.telecom_service_provisioning.service.implementations.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private InternetServiceManager internetServiceManager;

    @Mock
    private TvServiceManager tvServiceManager;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void subscribeToInternetService_Success() throws Exception {
        when(internetServiceManager.subscribeToService(any(Integer.class))).thenReturn(true);

        mockMvc.perform(post("/user/api/internet-service")
                        .param("serviceId", "1"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Successfully subscribed"));
    }

    @Test
    void subscribeToInternetService_PendingApproval() throws Exception {
        when(internetServiceManager.subscribeToService(any(Integer.class))).thenReturn(false);

        mockMvc.perform(post("/user/api/internet-service")
                        .param("serviceId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Requested for admins approval"));
    }

    @Test
    void subscribeToTvService_Success() throws Exception {
        when(tvServiceManager.subscribeToTvService(any(Integer.class))).thenReturn(true);

        mockMvc.perform(post("/user/api/tv-service")
                        .param("serviceId", "1"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Successfully subscribed"));
    }

    @Test
    void subscribeToTvService_PendingApproval() throws Exception {
        when(tvServiceManager.subscribeToTvService(any(Integer.class))).thenReturn(false);

        mockMvc.perform(post("/user/api/tv-service")
                        .param("serviceId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Requested for admins approval"));
    }

    @Test
    void getSubscribedServices() throws Exception {
        InternetServiceAvailed internetServiceAvailed = new InternetServiceAvailed();
        internetServiceAvailed.setUserId(1);
        internetServiceAvailed.setServiceId(1);
        internetServiceAvailed.setStartDate(LocalDate.now());
        internetServiceAvailed.setActive(true);

        TvServiceAvailed tvServiceAvailed = new TvServiceAvailed();
        tvServiceAvailed.setUserId(1);
        tvServiceAvailed.setServiceId(1);
        tvServiceAvailed.setStartDate(LocalDate.now());
        tvServiceAvailed.setActive(true);

        AvailedServices availedServices = new AvailedServices();
        availedServices.setInternetServicesAvailed(Collections.singletonList(internetServiceAvailed));
        availedServices.setTvServicesAvailed(Collections.singletonList(tvServiceAvailed));

        when(userService.getAllSubscribedServices()).thenReturn(availedServices);

        mockMvc.perform(get("/user/api/subscribed-services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.internetServicesAvailed").isArray())
                .andExpect(jsonPath("$.tvServicesAvailed").isArray());
    }

    @Test
    void getAvailableInternetServicesForUpgradeOrDowngrade() throws Exception {
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setServiceName("High Speed Internet");
        service.setDescription("Fast internet");
        service.setServiceType("Fiber");
        service.setServiceDownloadSpeed(100);
        service.setServiceUploadSpeed(50);
        service.setBenefits("High speed");
        service.setCriteria("No data cap");
        service.setActive(true);
        service.setCost(50.0);

        List<InternetService> services = Arrays.asList(service);

        when(internetServiceManager.getInternetServicesForUpgradeDowngrade(any(String.class), any(String.class))).thenReturn(services);

        mockMvc.perform(get("/user/api/internet-service/other")
                        .param("serviceName", "High Speed Internet")
                        .param("serviceType", "Fiber"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceId").value(1))
                .andExpect(jsonPath("$[0].serviceName").value("High Speed Internet"));
    }

    @Test
    void getAvailableTvServicesForUpgradeOrDowngrade() throws Exception {
        TvService service = new TvService();
        service.setServiceId(1);
        service.setServiceName("Premium TV Package");
        service.setDescription("All channels included");
        service.setBenefits("Premium channels");
        service.setCriteria("No blackout dates");
        service.setServiceType("Satellite");
        service.setActive(true);
        service.setCost(100.0);

        List<TvService> services = Arrays.asList(service);

        when(tvServiceManager.getTvServicesForUpgradeDowngrade(any(String.class), any(String.class))).thenReturn(services);

        mockMvc.perform(get("/user/api/tv-service/other")
                        .param("serviceName", "Premium TV Package")
                        .param("serviceType", "Satellite"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serviceId").value(1))
                .andExpect(jsonPath("$[0].serviceName").value("Premium TV Package"));
    }

    @Test
    void deactivateInternetService() throws Exception {
        mockMvc.perform(delete("/user/api/internet-service")
                        .param("availedServiceId", "1")
                        .param("startDate", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Internet service deactivated successfully"));

        verify(userService, times(1)).deactivateInternetService(any(Integer.class), any(LocalDate.class));
    }

    @Test
    void deactivateTvService() throws Exception {
        mockMvc.perform(delete("/user/api/tv-service")
                        .param("availedServiceId", "1")
                        .param("startDate", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("TV service deactivated successfully"));

        verify(userService, times(1)).deactivateTvService(any(Integer.class), any(LocalDate.class));
    }

    @Test
    void getCurrentUserDetails() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUsername("John Doe");
        userDetailsDto.setEmail("john.doe@example.com");

        when(userService.getUserDetails()).thenReturn(userDetailsDto);

        mockMvc.perform(get("/user/api/user-details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testGetPendingRequestsOfUser() {
        // Arrange
        List<PendingRequest> pendingRequests = new ArrayList<>(); // Empty or mocked list
        when(userService.getAllPendingRequests()).thenReturn(pendingRequests);

        // Act
        ResponseEntity<List<PendingRequest>> response = userController.getPendingRequestsOfUser();

        // Assert
        verify(userService).getAllPendingRequests(); // Verifies if service method is called
        assertThat(response.getBody()).isEqualTo(pendingRequests); // Asserts the response body
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // Asserts HTTP status code
    }

    @Test
    void testPostInternetServiceFeedback() throws Exception {
        // Arrange
        Integer availedServiceId = 1;
        String feedback = "Great service!";
        doNothing().when(userService).createInternetServiceFeedback(availedServiceId, feedback);

        // Act
        ResponseEntity<String> response = userController.postInternetServiceFeedback(availedServiceId, feedback);

        // Assert
        verify(userService).createInternetServiceFeedback(availedServiceId, feedback); // Verifies service method call
        assertThat(response.getBody()).isEqualTo("Internet service feedback created successfully"); // Asserts response message
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // Asserts HTTP status code
    }

    @Test
    void testPostTvServiceFeedback() throws Exception {
        // Arrange
        Integer availedServiceId = 2;
        String feedback = "Nice TV service!";
        doNothing().when(userService).createTvServiceFeedback(availedServiceId, feedback);

        // Act
        ResponseEntity<String> response = userController.postTvServiceFeedback(availedServiceId, feedback);

        // Assert
        verify(userService).createTvServiceFeedback(availedServiceId, feedback); // Verifies service method call
        assertThat(response.getBody()).isEqualTo("Tv service Feedback created successfully"); // Asserts response message
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // Asserts HTTP status code
    }



}


