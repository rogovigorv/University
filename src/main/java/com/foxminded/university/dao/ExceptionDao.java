package com.foxminded.university.dao;

import java.util.function.Supplier;

public class ExceptionDao extends RuntimeException implements Supplier<RuntimeException> {

    public ExceptionDao(String message) {
        super(message);
    }

    @Override
    public RuntimeException get() {
        return null;
    }
}
