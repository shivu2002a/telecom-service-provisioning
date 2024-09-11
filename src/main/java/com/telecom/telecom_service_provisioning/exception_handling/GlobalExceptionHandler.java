package com.telecom.telecom_service_provisioning.exception_handling;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import com.telecom.telecom_service_provisioning.exception_handling.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.EmailAlreadyTakenException;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.MaxServicesAlreadyAvailedException;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ResourceNotFoundException;
import com.telecom.telecom_service_provisioning.exception_handling.customExceptions.ServiceAlreadyAvailedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<ErrorResponse> emailAlreadyTakenException(EmailAlreadyTakenException e ) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceAlreadyAvailedException.class)
    public ResponseEntity<ErrorResponse> serviceAlreadyAvailedException(ServiceAlreadyAvailedException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MaxServicesAlreadyAvailedException.class)
    public ResponseEntity<ErrorResponse> maxServicesException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }




    


}
