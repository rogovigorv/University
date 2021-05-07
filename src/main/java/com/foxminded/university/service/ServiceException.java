package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;

public class ServiceException extends RuntimeException {

    public ServiceException(DaoException e) {
        super(e.getMessage());
    }
}
