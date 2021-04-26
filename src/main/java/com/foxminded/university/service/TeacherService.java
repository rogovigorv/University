package com.foxminded.university.service;

import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;

public class TeacherService {
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public void create(Teacher teacher) {
        teacherDao.create(teacher);
    }

    public Teacher getById(int id) {
        return teacherDao.getById(id);
    }

    public void update(Teacher teacher, int id) {
        teacherDao.update(teacher, id);
    }

    public void delete(int id) {
        teacherDao.delete(id);
    }
}
