package com.devpenha.clients.exceptions;

public class ResourceNotFoundException extends  RuntimeException {
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
