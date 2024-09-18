package com.telecom.telecom_service_provisioning.controller_testing;

import com.telecom.telecom_service_provisioning.controller.TvServiceController;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.service.implementations.TvServiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class TvServiceControllerTest {

    @Mock
    private TvServiceManager tvServiceManager;

    @InjectMocks
    private TvServiceController tvServiceController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tvServiceController).build();
    }

    @Test
    public void testGetAllTvServices() throws Exception {
        // Create mock data
        TvService tvService1 = new TvService();
        tvService1.setServiceId(1);
        tvService1.setServiceName("Premium TV");
        tvService1.setDescription("Premium TV package");
        tvService1.setBenefits("Includes all channels");
        tvService1.setCriteria("Requires subscription");
        tvService1.setServiceType("HD");
        tvService1.setActive(true);
        tvService1.setCost(99.99);

        TvService tvService2 = new TvService();
        tvService2.setServiceId(2);
        tvService2.setServiceName("Basic TV");
        tvService2.setDescription("Basic TV package");
        tvService2.setBenefits("Includes basic channels");
        tvService2.setCriteria("Requires subscription");
        tvService2.setServiceType("SD");
        tvService2.setActive(true);
        tvService2.setCost(49.99);

        List<TvService> tvServiceList = Arrays.asList(tvService1, tvService2);

        // Define the behavior of the mock service
        when(tvServiceManager.getAllTvService()).thenReturn(tvServiceList);

        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tv-services/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].serviceId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].serviceName").value("Premium TV"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].serviceId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].serviceName").value("Basic TV"));
    }

    @Test
    public void testGetTvServiceDetailsById_Success() throws Exception {
        // Create mock data
        TvService tvService = new TvService();
        tvService.setServiceId(1);
        tvService.setServiceName("Premium TV");
        tvService.setDescription("Premium TV package");
        tvService.setBenefits("Includes all channels");
        tvService.setCriteria("Requires subscription");
        tvService.setServiceType("HD");
        tvService.setActive(true);
        tvService.setCost(99.99);

        // Define the behavior of the mock service
        when(tvServiceManager.getTvServiceDetails(1)).thenReturn(tvService);

        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tv-services/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceName").value("Premium TV"));
    }

    @Test
    public void testGetTvServiceDetailsById() throws Exception {
        // Define the behavior of the mock service
        when(tvServiceManager.getTvServiceDetails(999)).thenReturn(null);

        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tv-services/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
   }
}

