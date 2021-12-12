package com.sap.configapi.handlers;


import com.sap.model.utils.AbstractErrorHandler;
import com.sap.configapi.controllers.ConditionsController;
import com.sap.model.dto.ErrorDetails;
import com.sap.model.exceptions.conditions.ConditionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;

@ControllerAdvice(basePackageClasses = ConditionsController.class)
public class ConditionsErrorHandler extends AbstractErrorHandler {

    @ExceptionHandler(ConditionNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ConditionNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> notFound(NoHandlerFoundException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
