package com.telecom.telecom_service_provisioning.service.implementations;

import com.telecom.telecom_service_provisioning.dto.ActivationMailDto;
import com.telecom.telecom_service_provisioning.dto.RegistrationMailDto;
import com.telecom.telecom_service_provisioning.dto.TerminationMailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;


@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private org.thymeleaf.TemplateEngine templateEngine;

    public void sendRegistrationMail(RegistrationMailDto registrationMailDto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,"UTF-8");
            helper.setFrom("telcoservice.helpdesk@gmail.com");

            // Load Thymeleaf template
            Context context = new Context();
            java.util.Map<String, Object> templateModel = new java.util.HashMap<>();
            templateModel.put("dto", registrationMailDto);
            context.setVariables(templateModel);  // Inject dynamic data into the template
            String html = templateEngine.process("Regi", context);

            helper.setTo(registrationMailDto.getEmail());
            helper.setSubject(registrationMailDto.getSubject());
            helper.setText(html, true);
            javaMailSender.send(message);
            LOGGER.info("Registration mail successfully sent to User : {}", registrationMailDto.getUsername());
        } catch (Exception e) {
            System.err.println("Failed to send registration email: " + e.getMessage());
        }
    }

    public void sendActivationMail(ActivationMailDto activationMailDto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,"UTF-8");
            helper.setFrom("telcoservice.helpdesk@gmail.com");

            // Load Thymeleaf template
            Context context = new Context();
            java.util.Map<String, Object> templateModel = new java.util.HashMap<>();
            templateModel.put("dto", activationMailDto);
            context.setVariables(templateModel);  // Inject dynamic data into the template
            String html = templateEngine.process("ServiceActivation", context);

            helper.setTo(activationMailDto.getEmail());
            helper.setSubject(activationMailDto.getSubject());
            helper.setText(html, true);

            javaMailSender.send(message);
            LOGGER.info("Service subscribed mail successfully sent to User : {}", activationMailDto.getUsername());
        } catch (Exception e) {
            System.err.println("Failed to send registration email: " + e.getMessage());
        }
    }

    public void sendTerminationMail(TerminationMailDto terminationMailDto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,"UTF-8");
            helper.setFrom("telcoservice.helpdesk@gmail.com");

            // Load Thymeleaf template
            Context context = new Context();
            java.util.Map<String, Object> templateModel = new java.util.HashMap<>();
            templateModel.put("dto", terminationMailDto);
            context.setVariables(templateModel);  // Inject dynamic data into the template
            String html = templateEngine.process("ServiceTermination", context);

            helper.setTo(terminationMailDto.getEmail());
            helper.setSubject(terminationMailDto.getSubject());
            helper.setText(html, true);
            javaMailSender.send(message);

            LOGGER.info("Service termination mail successfully sent to User : {}", terminationMailDto.getUsername());
        } catch (Exception e) {
            System.err.println("Failed to send registration email: " + e.getMessage());
        }
    }
}
