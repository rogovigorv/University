package com.foxminded.university.dao;

import com.foxminded.university.models.Group;

public interface UniversityDao<T> {

    public T getById(int id);

    public void delete(int id);
}
