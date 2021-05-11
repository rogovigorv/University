package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeacherService {
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public void create(Teacher teacher) {
        log.info("TeacherService create method started");

        try {
            teacherDao.create(teacher);
        } catch (DaoException e) {
            throw new ServiceException("Unable to create this teacher.", e);
        }
    }

    public Teacher getById(int id) {
        log.info("TeacherService getById method started");

        Teacher teacher;
        try {
            teacher = teacherDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't get teacher with ID " + id + ".", e);
        }

        return teacher;
    }

    public void update(Teacher teacher, int id) {
        log.info("TeacherService update method started");

        try {
            teacherDao.update(teacher, id);
        } catch (DaoException e) {
            throw new ServiceException("Unable to update teacher with ID " + id + ".", e);
        }
    }

    public void delete(int id) {
        log.info("TeacherService delete method started");

        try {
            teacherDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Unable to delete teacher with ID " + id + ".", e);
        }
    }
}
