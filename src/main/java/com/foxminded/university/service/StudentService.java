package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Student;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        log.info("Create student: {}", student);

        try {
            studentDao.create(student);
        } catch (DaoException e) {
            log.warn("Unable to create this student {}", student);
            throw new ServiceException("Unable to create this student.", e);
        }
    }

    public Student getById(int id) {
        log.info("Get student with ID: {}", id);

        Student student;
        try {
            student = studentDao.getById(id);
        } catch (DaoException e) {
            log.warn("Can't get student with ID: {}", id);
            throw new ServiceException("Can't get student with ID " + id + ".", e);
        }

        return student;
    }

    public void update(Student student, int id) {
        log.info("Update student: {}, and ID: {}", student, id);

        try {
            studentDao.update(student, id);
        } catch (DaoException e) {
            log.warn("Unable to update student with ID: {}", id);
            throw new ServiceException("Unable to update student with ID " + id + ".", e);
        }
    }

    public void delete(int id) {
        log.info("Delete student with ID: {}", id);

        try {
            studentDao.delete(id);
        } catch (DaoException e) {
            log.warn("Unable to delete student with ID: {}", id);
            throw new ServiceException("Unable to delete student with ID " + id + ".", e);
        }
    }

    public void deleteByGroupName(String groupName) {
        log.info("Delete student by group name: {}", groupName);

        Group group;
        try {
            group = groupDao.getByGroupName(groupName);
        } catch (DaoException e) {
            log.warn("Unable to get group with name {} to this student", groupName);
            throw new ServiceException("Unable to get group to this student.", e);
        }

        try {
            studentDao.delete(group.getId());
        } catch (DaoException e) {
            log.warn("Unable to delete student with group ID: {}", group.getId());
            throw new ServiceException("Unable to delete student with group ID " + group.getId() + ".", e);
        }
    }

    public void changeGroup(int studentId, String groupName) {
        log.info("Change student group: student ID - " + studentId + ", new group - " + groupName);

        Group group;
        try {
            group = groupDao.getByGroupName(groupName);
        } catch (DaoException e) {
            log.warn("Unable to get group with name {} to this student", groupName);
            throw new ServiceException("Unable to get group to this student.", e);
        }

        try {
            studentDao.updateGroup(studentId, group.getId());
        } catch (DaoException e) {
            log.warn("Unable to change the group for student with ID: {}", studentId);
            throw new ServiceException("Unable to change the group for student with ID " + studentId + ".", e);
        }
    }

    public Page<Student> findPaginated(Pageable pageable) {
        log.debug("Get students pages");

        List<Student> students = studentDao.showAll();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Student> currentPageList;

        if (students.size() < startItem) {
            currentPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, students.size());
            currentPageList = students.subList(startItem, toIndex);
        }

        Page<Student> studentPage =
                new PageImpl<>(currentPageList, PageRequest.of(currentPage, pageSize), students.size());

        return studentPage;
    }
}
