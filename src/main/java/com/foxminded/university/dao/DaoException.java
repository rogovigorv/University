package com.foxminded.university.dao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
        log.info("A DaoException exception occurred");
    }
}
