package com.telecom.telecom_service_provisioning.service.implementations;

import com.telecom.telecom_service_provisioning.dto.ActivationMailDto;
import com.telecom.telecom_service_provisioning.dto.RegistrationMailDto;
import com.telecom.telecom_service_provisioning.dto.TerminationMailDto;
import com.telecom.telecom_service_provisioning.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EmailMiddlewareService {

    public static final String REGISTRATION_SUBJECT = "Registered on Telcoservice";

    @Autowired
    private EmailService emailService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void sendRegistrationMail(User user) {
        RegistrationMailDto dto = new RegistrationMailDto();
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setSubject(REGISTRATION_SUBJECT);
        dto.setUsername(user.getUsername());
        Runnable task = () -> {
            emailService.sendRegistrationMail(dto);
        };
        Runnable securityContextTask = new DelegatingSecurityContextRunnable(task);
        executorService.submit(securityContextTask);
    }


    public void sendServiceActivationMail(ActivationMailDto dto) {
        Runnable task = () -> {
            emailService.sendActivationMail(dto);
        };
        Runnable securityContextTask = new DelegatingSecurityContextRunnable(task);
        executorService.submit(securityContextTask);
    }

    public void sendServiceTerminationMail(TerminationMailDto dto) {
        Runnable task = () -> {
            emailService.sendTerminationMail(dto);
        };
        Runnable securityContextTask = new DelegatingSecurityContextRunnable(task);
        executorService.submit(securityContextTask);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

}
