package com.foxminded.university.dao;

import java.util.function.Supplier;

public class DaoException extends RuntimeException implements Supplier<RuntimeException> {

    public DaoException(String message) {
        super(message);
    }

    @Override
    public RuntimeException get() {
        return null;
    }
}
