package com.sap.model.exceptions.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PageRequestOutOfBounds extends Exception {

    private static final long serialVersionUID = 1L;

    public PageRequestOutOfBounds(String message){
        super(message);
    }

}
