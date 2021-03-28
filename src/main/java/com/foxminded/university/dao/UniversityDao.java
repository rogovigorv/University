package com.foxminded.university.dao;

public interface UniversityDao<T> {

    public T getById(int id);

    public void delete(int id);
}
