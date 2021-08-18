package com.foxminded.university.repository;

import java.util.List;

public interface UniversityRepository<T> {

    void create(T t);

    T getById(int id);

    void delete(int id);

    List<T> showAll();

    void update(T T);
}
