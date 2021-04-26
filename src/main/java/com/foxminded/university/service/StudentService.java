package com.foxminded.university.service;

import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.models.Student;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentService {
    private final StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void create(Student student) {
        studentDao.create(student);
    }

    public Student getById(int id) {
        return studentDao.getById(id);
    }

    public void update(Student student, int id) {
        studentDao.update(student, id);
    }

    public void delete(int id) {
        studentDao.delete(id);
    }
}
