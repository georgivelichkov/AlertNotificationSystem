package com.sap.model.exceptions.subscriptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SubscriptionNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public SubscriptionNotFoundException(String message){
        super(message);
    }
}