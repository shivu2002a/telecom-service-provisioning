// package com.telecom.telecom_service_provisioning;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.util.Arrays;
// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.telecom.telecom_service_provisioning.controller.InternetServiceController;
// import com.telecom.telecom_service_provisioning.model.InternetService;
// import com.telecom.telecom_service_provisioning.service.implementations.InternetServiceManager;

// @WebMvcTest(InternetServiceController.class)
// class InternetServiceControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private InternetServiceManager internetServiceManager;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     void getAllInternetServices_ReturnsListOfServices() throws Exception {
//         InternetService service1 = new InternetService();
//         service1.setServiceId(1);
//         service1.setServiceName("Fiber");
//         service1.setDescription("High-speed fiber internet");
//         service1.setServiceType("Fiber");
//         service1.setServiceDownloadSpeed(1000);
//         service1.setServiceUploadSpeed(100);
//         service1.setBenefits("Unlimited data");
//         service1.setCriteria("Residential");
//         service1.setActive(true);
//         service1.setMonthlyCost(50.0);

//         InternetService service2 = new InternetService();
//         service2.setServiceId(2);
//         service2.setServiceName("DSL");
//         service2.setDescription("Reliable DSL internet");
//         service2.setServiceType("DSL");
//         service2.setServiceDownloadSpeed(50);
//         service2.setServiceUploadSpeed(10);
//         service2.setBenefits("Low cost");
//         service2.setCriteria("Residential");
//         service2.setActive(true);
//         service2.setMonthlyCost(20.0);

//         List<InternetService> mockServices = Arrays.asList(service1, service2);

//         when(internetServiceManager.getAllInternetService()).thenReturn(mockServices);

//         // Act & Assert
//         mockMvc.perform(get("/api/internet-services"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.size()").value(2)) // Check that 2 services are returned
//                 .andExpect(jsonPath("$[0].serviceName").value("Fiber"))
//                 .andExpect(jsonPath("$[0].description").value("High-speed fiber internet"))
//                 .andExpect(jsonPath("$[0].serviceType").value("Fiber"))
//                 .andExpect(jsonPath("$[0].serviceDownloadSpeed").value(1000))
//                 .andExpect(jsonPath("$[0].serviceUploadSpeed").value(100))
//                 .andExpect(jsonPath("$[0].monthlyCost").value(50.0))
//                 .andExpect(jsonPath("$[1].serviceName").value("DSL"))
//                 .andExpect(jsonPath("$[1].serviceDownloadSpeed").value(50))
//                 .andDo(print());  // Logs the request and response
//     }
// }
