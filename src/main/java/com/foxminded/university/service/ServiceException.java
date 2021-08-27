package com.foxminded.university.service;

public class ServiceException extends RuntimeException {

    public ServiceException(String message, Throwable e) {
        super(message, e);
    }
}
