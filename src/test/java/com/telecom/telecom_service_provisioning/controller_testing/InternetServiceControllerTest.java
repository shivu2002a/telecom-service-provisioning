package com.telecom.telecom_service_provisioning.controller_testing;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.telecom.telecom_service_provisioning.controller.InternetServiceController;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.service.implementations.InternetServiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class InternetServiceControllerTest {

    @Mock
    private InternetServiceManager internetServiceManager;

    @InjectMocks
    private InternetServiceController internetServiceController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(internetServiceController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllInternetServices() throws Exception {
        // Create mock data
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setServiceName("Basic Internet");
        service.setDescription("Basic internet service");
        service.setBenefits("Low cost");
        service.setCriteria("No contract");
        service.setServiceType("Fiber");
        service.setActive(true);
        service.setCost(19.99);

        // Define the behavior of the mock service
        when(internetServiceManager.getAllInternetService()).thenReturn(Collections.singletonList(service));

        // Perform the test
        mockMvc.perform(get("/api/internet-services/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(service))));
    }

    @Test
    public void testGetInternetServiceDetailsById() throws Exception {
        // Create mock data
        InternetService service = new InternetService();
        service.setServiceId(1);
        service.setServiceName("Premium Internet");
        service.setDescription("Premium internet service");
        service.setBenefits("High speed");
        service.setCriteria("Contract required");
        service.setServiceType("Fiber");
        service.setActive(true);
        service.setCost(49.99);

        // Define the behavior of the mock service
        when(internetServiceManager.getInternetServiceDetails(1)).thenReturn(service);

        // Perform the test
        mockMvc.perform(get("/api/internet-services/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(service)));
    }
    @Test
    public void testGetInternetServiceDetailsById_NotFound() throws Exception {
        // Define the behavior of the mock service
        when(internetServiceManager.getInternetServiceDetails(999)).thenReturn(null);

        // Perform the test
        mockMvc.perform(get("/api/internet-services/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

