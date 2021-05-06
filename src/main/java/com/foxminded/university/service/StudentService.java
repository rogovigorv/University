package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {
    private final StudentDao studentDao;
    private final GroupDao groupDao;

    @Autowired
    public StudentService(StudentDao studentDao, GroupDao groupDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
    }

    public void create(Student student) {
        log.info("StudentService create method started");
        studentDao.create(student);
    }

    public Student getById(int id) {
        log.info("StudentService getById method started");
        return studentDao.getById(id);
    }

    public void update(Student student, int id) {
        log.info("StudentService update method started");
        studentDao.update(student, id);
    }

    public void delete(int id) {
        log.info("StudentService delete method started");
        studentDao.delete(id);
    }

    public void deleteByGroupName(String groupName) {
        log.info("StudentService deleteByGroupName method started");

        Group group = groupDao.getByGroupName(groupName);
        studentDao.delete(group.getId());
    }

    public void changeGroup(int studentId, String groupName) {
        log.info("StudentService changeGroup method started");

        Group group = groupDao.getByGroupName(groupName);
        studentDao.updateGroup(studentId, group.getId());
    }
}
