package com.foxminded.university.dao;

import java.util.List;

public interface UniversityDao<T> {

    void create(T t);

    T getById(int id);

    void delete(int id);

    List<T> showAll();

    void update(T T);
}
