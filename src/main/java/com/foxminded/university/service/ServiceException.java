package com.foxminded.university.service;

import com.foxminded.university.repository.RepositoryException;

public class ServiceException extends RuntimeException {

    public ServiceException(RepositoryException e) {
        super(e.getMessage());
    }

    public ServiceException(String message, RepositoryException e) {
        super(message, e);
    }
}
