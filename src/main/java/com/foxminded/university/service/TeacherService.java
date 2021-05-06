package com.foxminded.university.service;

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
        teacherDao.create(teacher);
    }

    public Teacher getById(int id) {
        log.info("TeacherService getById method started");
        return teacherDao.getById(id);
    }

    public void update(Teacher teacher, int id) {
        log.info("TeacherService update method started");
        teacherDao.update(teacher, id);
    }

    public void delete(int id) {
        log.info("TeacherService delete method started");
        teacherDao.delete(id);
    }
}
