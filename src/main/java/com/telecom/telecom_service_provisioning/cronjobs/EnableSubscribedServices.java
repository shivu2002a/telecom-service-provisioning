package com.telecom.telecom_service_provisioning.cronjobs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.telecom.telecom_service_provisioning.model.InternetServiceAvailed;
import com.telecom.telecom_service_provisioning.model.TvServiceAvailed;
import com.telecom.telecom_service_provisioning.repository.InternetServiceAvailedRepository;
import com.telecom.telecom_service_provisioning.repository.TvServiceAvailedRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
public class EnableSubscribedServices {
    
    @Autowired
    private InternetServiceAvailedRepository internetServiceAvailedRepo;

    @Autowired
    private TvServiceAvailedRepository tvServiceAvailedRepo;

    @Scheduled(cron = "0 0 0 * * *")
    private void enableInternetServiceInQeue(){
        LOGGER.info("EnableInternetServiceInQeue called from: {}", this.getClass().getName());;
        List<InternetServiceAvailed> internetServices = internetServiceAvailedRepo.findByStartDate(LocalDate.now());
        if(internetServices == null) return;
        for (InternetServiceAvailed internetServiceAvailed : internetServices) {
            internetServiceAvailed.setActive(true);
            internetServiceAvailedRepo.save(internetServiceAvailed);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void enableAvailedTvServiceInQeue() {
        LOGGER.info("EnableAvailedTvServiceInQeue called from: {}", this.getClass().getName());;
        List<TvServiceAvailed> tvServices = tvServiceAvailedRepo.findByStartDate(LocalDate.now());
        if(tvServices == null) return;
        for (TvServiceAvailed tvServiceAvailed : tvServices) {
            tvServiceAvailed.setActive(true);
            tvServiceAvailedRepo.save(tvServiceAvailed);
        }
    }
    

}
