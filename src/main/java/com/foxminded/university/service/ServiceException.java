package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;

public class ServiceException extends RuntimeException {

    public ServiceException(DaoException e) {
        super(e.getMessage());
    }

    public ServiceException(String message, DaoException e) {
        super(message, e);
    }
}
