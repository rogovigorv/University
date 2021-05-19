package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.LectureDao;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LectureService {
    private final LectureDao lectureDao;
    private final TeacherDao teacherDao;

    @Autowired
    public LectureService(LectureDao lectureDao, TeacherDao teacherDao) {
        this.lectureDao = lectureDao;
        this.teacherDao = teacherDao;
    }

    public void create(Lecture lecture, int groupID) {
        log.info("Create lecture: {} and group ID: {}", lecture, groupID);

        try {
            lectureDao.create(lecture, groupID);
        } catch (DaoException e) {
            log.warn("Unable to create this lecture: {}", lecture);
            throw new ServiceException("Unable to create this lecture.", e);
        }
    }

    public Lecture getById(int id) {
        log.info("Get lecture with ID: {}", id);

        Lecture lecture;
        try {
            lecture = lectureDao.getById(id);
        } catch (DaoException e) {
            log.warn("Can't get lecture with ID: {}", id);
            throw new ServiceException("Can't get lecture with ID " + id + ".", e);
        }

        return lecture;
    }

    public Lecture getByGroupId(int id) {
        log.info("Get lecture with group ID: {}", id);

        Lecture lecture;
        try {
            lecture = lectureDao.getByGroupId(id);
        } catch (DaoException e) {
            log.warn("Can't get lecture with group ID: {}", id);
            throw new ServiceException("Can't get lecture with group ID " + id + ".", e);
        }

        return lecture;
    }

    public void update(Lecture lecture, int groupID, int id) {
        log.info("Update lecture: {}, group ID: {} and ID: {}", lecture, groupID, id);

        try {
            lectureDao.update(lecture, groupID, id);
        } catch (DaoException e) {
            log.warn("Unable to update lecture with ID: {}", id);
            throw new ServiceException("Unable to update lecture with ID " + id + ".", e);
        }
    }

    public void delete(int id) {
        log.info("Delete lecture with ID: {}", id);

        try {
            lectureDao.delete(id);
        } catch (DaoException e) {
            log.warn("Unable to delete lecture with ID: {}", id);
            throw new ServiceException("Unable to delete lecture with ID " + id + ".", e);
        }
    }

    public void deleteByTeacherSurname(String surname) {
        log.info("Delete lecture with teacher surname: {}", surname);

        Teacher teacher;
        try {
            teacher = teacherDao.getByTeacherSurname(surname);
        } catch (DaoException e) {
            log.warn("Unable to get teacher with surname {} to this lecture", surname);
            throw new ServiceException("Unable to get teacher to this lecture.", e);
        }

        try {
            lectureDao.deleteByTeacherId(teacher.getId());
        } catch (DaoException e) {
            log.warn("Unable to delete lecture with teacher ID: {}", teacher.getId());
            throw new ServiceException("Unable to delete lecture with teacher ID " + teacher.getId() + ".", e);
        }
    }
}
