package com.telecom.telecom_service_provisioning.service.implementations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.dto.FeedbackDto;
import com.telecom.telecom_service_provisioning.dto.MostAvailedServicesDto;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.model.InternetService;
import com.telecom.telecom_service_provisioning.model.TvService;
import com.telecom.telecom_service_provisioning.repository.InternetServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.InternetServiceRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceRepository;
import com.telecom.telecom_service_provisioning.service.Interfaces.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private InternetServiceRepository internetServiceRepo;

    @Autowired
    private TvServiceRepository tvServiceRepository;

    @Autowired
    private InternetServiceAvailedRepository internetServiceAvailedRepo;

    @Autowired
    private TvServiceAvailedRepository tvServiceAvailedRepo;

    @Autowired
    private FeedbackService feedbackService;

    public InternetService createInternetService(InternetService internetService) {
        internetService.setActive(true);
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
        updatedService.setCost(updates.getCost());
        updatedService.setValidity(updates.getValidity());
        updatedService.setActive(true);
        return internetServiceRepo.save(updatedService);
    }

    public TvService createTvService(TvService tvService) {
        tvService.setActive(true);
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
        updatedService.setServiceType(updates.getServiceType());
        updatedService.setCost(updates.getCost());
        updatedService.setValidity(updates.getValidity());
        updatedService.setActive(true);
        return tvServiceRepository.save(updatedService);
    }

    public List<MostAvailedServicesDto> getMostAvailedInternetService() {
        List<Object[]> availedServices = internetServiceAvailedRepo.findServiceIdAndCount();
        List<MostAvailedServicesDto> res = new ArrayList<>();
        for (Object[] result : availedServices) {
            Integer serviceId = Integer.parseInt(result[0]+"");
            Integer count = Integer.parseInt(result[1]+"");
            MostAvailedServicesDto dto = new MostAvailedServicesDto();
            InternetService internetService = internetServiceRepo.findById(serviceId).get();
            dto.setServiceId(serviceId);
            dto.setServiceName(internetService.getServiceName());
            dto.setServiceType(internetService.getServiceType());
            dto.setSubscribedCount(count);
            res.add(dto);
        }
        return res;
    }

    public List<MostAvailedServicesDto> getMostAvailedTvService() {
        List<Object[]> availedServices = tvServiceAvailedRepo.findServiceIdAndCount();
        List<MostAvailedServicesDto> res = new ArrayList<>();
        for (Object[] result : availedServices) {
            Integer serviceId = Integer.parseInt(result[0]+"");
            Integer count = Integer.parseInt(result[1]+"");
            MostAvailedServicesDto dto = new MostAvailedServicesDto();
            TvService tvService = tvServiceRepository.findById(serviceId).get();
            dto.setServiceId(serviceId);
            dto.setServiceName(tvService.getServiceName());
            dto.setServiceType(tvService.getServiceType());
            dto.setSubscribedCount(count);
            res.add(dto);
        }
        return res;
    }

    public FeedbackDto getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

}
