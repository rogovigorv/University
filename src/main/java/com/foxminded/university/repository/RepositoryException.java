package com.foxminded.university.repository;

public class RepositoryException extends RuntimeException {

    public RepositoryException(Throwable e) {
        super(e.getMessage());
    }
}
