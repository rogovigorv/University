package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Student;
import com.foxminded.university.models.Teacher;
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
public class TeacherService {
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public void create(Teacher teacher) {
        log.info("Create teacher: {}", teacher);

        try {
            teacherDao.create(teacher);
        } catch (DaoException e) {
            log.warn("Unable to create this teacher {}", teacher);
            throw new ServiceException("Unable to create this teacher.", e);
        }
    }

    public Teacher getById(int id) {
        log.info("Get teacher with ID: {}", id);

        Teacher teacher;
        try {
            teacher = teacherDao.getById(id);
        } catch (DaoException e) {
            log.warn("Can't get teacher with ID: {}", id);
            throw new ServiceException("Can't get teacher with ID " + id + ".", e);
        }

        return teacher;
    }

    public void update(Teacher teacher, int id) {
        log.info("Update teacher: {}, and ID: {}", teacher, id);

        try {
            teacherDao.update(teacher, id);
        } catch (DaoException e) {
            log.warn("Unable to update teacher with ID: {}", id);
            throw new ServiceException("Unable to update teacher with ID " + id + ".", e);
        }
    }

    public void delete(int id) {
        log.info("Delete teacher with ID: {}", id);

        try {
            teacherDao.delete(id);
        } catch (DaoException e) {
            log.warn("Unable to delete teacher with ID: {}", id);
            throw new ServiceException("Unable to delete teacher with ID " + id + ".", e);
        }
    }

    public Page<Teacher> findPaginated(Pageable pageable) {
        log.debug("Get teachers pages");

        List<Teacher> teachers = teacherDao.showAll();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Teacher> currentPageList;

        if (teachers.size() < startItem) {
            currentPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, teachers.size());
            currentPageList = teachers.subList(startItem, toIndex);
        }

        Page<Teacher> teacherPage =
                new PageImpl<>(currentPageList, PageRequest.of(currentPage, pageSize), teachers.size());

        return teacherPage;
    }
}
