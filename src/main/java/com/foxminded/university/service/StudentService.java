package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentDao studentDao;
    private final GroupDao groupDao;

    @Autowired
    public StudentService(StudentDao studentDao, GroupDao groupDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
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

    public void deleteByGroupName(String groupName) {
        Group group = groupDao.getByGroupName(groupName);
        studentDao.delete(group.getId());
    }

    public void changeGroup(int studentId, String groupName) {
        Group group = groupDao.getByGroupName(groupName);
        studentDao.updateGroup(studentId, group.getId());
    }
}
