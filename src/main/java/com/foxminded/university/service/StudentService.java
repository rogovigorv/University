package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;
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

        try {
            studentDao.create(student);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Student getById(int id) {
        log.info("StudentService getById method started");

        Student student;
        try {
            student = studentDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return student;
    }

    public void update(Student student, int id) {
        log.info("StudentService update method started");

        try {
            studentDao.update(student, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(int id) {
        log.info("StudentService delete method started");

        try {
            studentDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteByGroupName(String groupName) {
        log.info("StudentService deleteByGroupName method started");

        Group group;
        try {
            group = groupDao.getByGroupName(groupName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        try {
            studentDao.delete(group.getId());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeGroup(int studentId, String groupName) {
        log.info("StudentService changeGroup method started");

        Group group;
        try {
            group = groupDao.getByGroupName(groupName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        try {
            studentDao.updateGroup(studentId, group.getId());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
