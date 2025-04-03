package com.artocons.carshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceVaidationException  extends Exception{

    private static final long serialVersionUID = 1L;
    public ResourceVaidationException(String message){
        super(message);
    }
}
