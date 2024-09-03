package com.telecom.telecom_service_provisioning.dto;

import java.util.List;

import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;

import lombok.Data;

@Data
public class AvailedServices {
    private List<InternetServiceAvailed> internetServicesAvailed;
    private List<TvServiceAvailed> tvServicesAvailed;
}