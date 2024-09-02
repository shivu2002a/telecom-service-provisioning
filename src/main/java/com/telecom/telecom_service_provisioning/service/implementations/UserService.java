// package com.telecom.telecom_service_provisioning.service.implementations;
// import java.time.LocalDate;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;

// import com.telecom.telecom_service_provisioning.model.InternetService;
// import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
// import com.telecom.telecom_service_provisioning.model.compositekeyModels.InternetServicesAvailedId;
// import com.telecom.telecom_service_provisioning.repository.InternetServiceAvailedRepository;
// import com.telecom.telecom_service_provisioning.repository.InternetServiceRepository;

// public class UserService {

//     @Autowired
//     private InternetServiceAvailedRepository internetServiceAvailedRepository;

//     public List<InternetServiceAvailed> getActiveSubscribedServices(Integer userId) {
//         List<InternetServiceAvailed> allServices = internetServiceAvailedRepository.findByUserId(userId);
//         return allServices.stream()
//                 // .filter(InternetServiceAvailed::getActive)
//                 .collect(Collectors.toList());
//     }

//     public void deactivateService(Integer userId, Integer serviceId, LocalDate startDate) {
//         InternetServicesAvailedId id = new InternetServicesAvailedId(userId, serviceId, startDate);
//         Optional<InternetServiceAvailed> serviceAvailed = internetServiceAvailedRepository.findById(id);
//         if (serviceAvailed.isPresent()) {
//             InternetServiceAvailed serviceToUpdate = serviceAvailed.get();
//             // serviceToUpdate.setActive(false);
//             internetServiceAvailedRepository.save(serviceToUpdate);
//         }
//     }

//      @Autowired
//     private InternetServiceRepository internetServiceRepository;

//     public List<InternetService> findAllExceptType(String excludedServiceType) {
//         return internetServiceRepository.findByServiceTypeNot(excludedServiceType);
//     }

//     public List<InternetService> findAll() {
//         return internetServiceRepository.findAll();
//     }
    
// }
