package com.foxminded.university.dao;

public class DaoException extends RuntimeException {

    public DaoException(Throwable e) {
        super(e.getMessage());
    }
}
