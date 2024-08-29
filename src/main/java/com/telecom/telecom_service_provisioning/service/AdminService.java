package com.telecom.telecom_service_provisioning.service;

import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.repository.InternetServiceRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AdminService {

    @Autowired
    private InternetServiceRepository internetServiceRepo;

    @Autowired
    private TvServiceRepository tvServiceRepository;

    public InternetService createInternetService(InternetService internetService) {
        return internetServiceRepo.save(internetService);
    }

    public List<InternetService> getAllInternetService(){
    return internetServiceRepo.findByActiveTrue();
    }

    public InternetService updatePartialInternetService(Map<String,Object> updates,InternetService updatedService){
        updates.forEach((key, value) -> {
            switch (key) {
                case "serviceName":
                    updatedService.setServiceName((String) value);
                    break;
                case "description":
                    updatedService.setDescription((String) value);
                    break;
                case "serviceType":
                    updatedService.setServiceType((String) value);
                    break;
                case "serviceDownloadSpeed":
                    updatedService.setServiceDownloadSpeed((Integer) value);
                    break;
                case "serviceUploadSpeed":
                    updatedService.setServiceUploadSpeed((Integer) value);
                    break;
                case "benefits":
                    updatedService.setBenefits((String) value);
                    break;
                case "criteria":
                    updatedService.setCriteria((String) value);
                    break;
                case "monthlyCost":
                    updatedService.setMonthlyCost((Double) value);
                    break;
            }
        });

        // Set the updated service as active
        updatedService.setActive(true);

        // Save the updated service
        return internetServiceRepo.save(updatedService);
    }

    public InternetService terminateInternetService(InternetService internetService){
        internetService.setActive(false);
        return internetServiceRepo.save(internetService);
    }

    public TvService createTvService(TvService tvService) {
        return tvServiceRepository.save(tvService);
    }

    public List<TvService> getAllTvService(){
        return tvServiceRepository.findByActiveTrue();
    }

    public TvService updatePartialTvService(Map<String,Object> updates, TvService updatedService){
        updates.forEach((key, value) -> {
            switch (key) {
                case "serviceName":
                    updatedService.setServiceName((String) value);
                    break;
                case "description":
                    updatedService.setDescription((String) value);
                    break;
                case "benefits":
                    updatedService.setBenefits((String) value);
                    break;
                case "criteria":
                    updatedService.setCriteria((String) value);
                    break;
                case "monthlyCost":
                    updatedService.setMonthlyCost((Double) value);
                    break;
            }
        });

        // Set the updated service as active
        updatedService.setActive(true);

        // Save the updated service
        return tvServiceRepository.save(updatedService);
    }

    public TvService terminateTvService(TvService tvService){
        tvService.setActive(false);
        return tvServiceRepository.save(tvService);
    }

}

