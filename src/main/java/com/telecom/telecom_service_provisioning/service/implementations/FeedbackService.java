package com.telecom.telecom_service_provisioning.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.telecom_service_provisioning.dto.FeedbackDto;
import com.telecom.telecom_service_provisioning.model.InternetServiceFeedback;
import com.telecom.telecom_service_provisioning.model.TvServiceFeedback;
import com.telecom.telecom_service_provisioning.repository.InternetServiceFeedbackRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceFeedbackRepository;
import com.telecom.telecom_service_provisioning.service.Interfaces.FeedbackServiceInterface;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FeedbackService implements FeedbackServiceInterface {
    
    @Autowired
    private TvServiceFeedbackRepository tvServiceFeedbackRepo;

    @Autowired
    private InternetServiceFeedbackRepository internetServiceFeedbackRepo;

    @Autowired
    private AuthenticationServiceImpl authservice;

    @Autowired
    private InternetServiceManager internetServiceManager;

    @Autowired
    private TvServiceManager tvServiceManager;


	public void createInternetServiceFeedback(Integer availedServiceId, String feedback) throws Exception {
		InternetServiceFeedback feedbackObj = new InternetServiceFeedback();
        feedbackObj.setFeedback(feedback);
        feedbackObj.setUsername(authservice.getCurrentUserDetails().getUsername());
        feedbackObj.setInternetService(internetServiceManager.getInternetServiceDetails(availedServiceId));
        LOGGER.info("Saving the internetservice feedback: {}", feedbackObj);
        internetServiceFeedbackRepo.save(feedbackObj);
	}

    public void createTvServiceFeedback(Integer availedServiceId, String feedback) throws Exception {
        TvServiceFeedback feedbackObj = new TvServiceFeedback();
        feedbackObj.setFeedback(feedback);
        feedbackObj.setUsername(feedback);
        feedbackObj.setUsername(authservice.getCurrentUserDetails().getUsername());
        feedbackObj.setTvService(tvServiceManager.getTvServiceDetails(availedServiceId));
        tvServiceFeedbackRepo.save(feedbackObj);
    }

    public FeedbackDto getAllFeedbacks() {
        FeedbackDto feedbacks = new FeedbackDto();
        feedbacks.setInternetServiceFeedbacks(internetServiceFeedbackRepo.findAll());
        feedbacks.setTvServiceFeedbacks(tvServiceFeedbackRepo.findAll());
        return feedbacks;
    }


}
