package com.telecom.telecom_service_provisioning.service.implementations;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.repository.InternetServiceRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.exceptionHandling.CustomExceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl {

    @Autowired
    private InternetServiceRepository internetServiceRepo;

    @Autowired
    private TvServiceRepository tvServiceRepository;

    public InternetService createInternetService(InternetService internetService) {
        return internetServiceRepo.save(internetService);
    }

    public List<InternetService> getAllInternetService(){
        return internetServiceRepo.findAll();
    }

    public void terminateInternetService(Integer id) throws ResourceNotFoundException {
        Optional<InternetService> optInternetService = internetServiceRepo.findById(id);
        if(optInternetService.isPresent()) {
            InternetService internetService = optInternetService.get();
            internetService.setActive(false);
            internetServiceRepo.save(internetService);
            return;
        }
        throw new ResourceNotFoundException("TvService with id: " + id + "doesn't exist");
    }

    public InternetService updateInternetService(InternetService updates) {
        Optional<InternetService> optionalService = internetServiceRepo.findById(updates.getServiceId());
        InternetService existingService = optionalService.get();
        existingService.setActive(false);
        internetServiceRepo.save(existingService);
        InternetService updatedService=new InternetService();
        updatedService.setServiceName(updates.getServiceName());
        updatedService.setDescription(updates.getDescription());
        updatedService.setServiceType(updates.getServiceType());
        updatedService.setServiceDownloadSpeed(updates.getServiceDownloadSpeed());
        updatedService.setServiceUploadSpeed(updates.getServiceUploadSpeed());
        updatedService.setBenefits(updates.getBenefits());
        updatedService.setCriteria(updates.getCriteria());
        updatedService.setMonthlyCost(updates.getMonthlyCost());
        updatedService.setActive(true);
        return internetServiceRepo.save(updatedService);
    }

    public TvService createTvService(TvService tvService) {
        return tvServiceRepository.save(tvService);
    }

    public List<TvService> getAllTvServices(){
        return tvServiceRepository.findAll();
    }

    public void terminateTvService(Integer id) throws ResourceNotFoundException {
        Optional<TvService> optTvService = tvServiceRepository.findById(id);
        if(optTvService.isPresent()) {
            TvService tvService = optTvService.get();
            tvService.setActive(false);
            tvServiceRepository.save(tvService);
            return;
        }
        throw new ResourceNotFoundException("TvService with id: " + id + "doesn't exist");
    }

    public TvService updateTvService(TvService updates) {
        Optional<TvService> optionalService = tvServiceRepository.findById(updates.getServiceId());
        TvService existingService = optionalService.get();
        existingService.setActive(false);
        tvServiceRepository.save(existingService);
        TvService updatedService=new TvService();
        updatedService.setServiceName(updates.getServiceName());
        updatedService.setDescription(updates.getDescription());
        updatedService.setBenefits(updates.getBenefits());
        updatedService.setCriteria(updates.getCriteria());
        updatedService.setMonthlyCost(updates.getMonthlyCost());
        updatedService.setActive(true);
        return tvServiceRepository.save(updatedService);
    }

}
