package com.sap.model.exceptions.events;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EventNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EventNotFoundException(String message){
        super(message);
    }
}
