package com.sap.model.utils;

import com.sap.model.dto.ErrorDetails;
import com.sap.model.exceptions.response.PageRequestOutOfBounds;
import com.sap.model.exceptions.response.ResultsRequestOutOfBounds;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

public abstract class AbstractErrorHandler {

    @ExceptionHandler({PageRequestOutOfBounds.class, ResultsRequestOutOfBounds.class})
    public ResponseEntity<?> handleOutOfBounds(PageRequestOutOfBounds e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleInvalidRequestParam(NumberFormatException e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Invalid Request Parameter:"+e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
