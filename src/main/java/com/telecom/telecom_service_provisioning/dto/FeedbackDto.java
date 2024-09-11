package com.telecom.telecom_service_provisioning.dto;

import java.util.*;

import com.telecom.telecom_service_provisioning.model.InternetServiceFeedback;
import com.telecom.telecom_service_provisioning.model.TvServiceFeedback;

import lombok.Data;

@Data
public class FeedbackDto {
    private List<InternetServiceFeedback> internetServiceFeedbacks;    
    private List<TvServiceFeedback> tvServiceFeedbacks;    
}
