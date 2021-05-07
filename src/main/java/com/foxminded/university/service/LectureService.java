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
        log.info("LectureService create method started");

        try {
            lectureDao.create(lecture, groupID);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Lecture getById(int id) {
        log.info("LectureService getById method started");

        Lecture lecture;
        try {
            lecture = lectureDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return lecture;
    }

    public Lecture getByGroupId(int id) {
        log.info("LectureService getByGroupId method started");

        Lecture lecture;
        try {
            lecture = lectureDao.getByGroupId(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return lecture;
    }

    public void update(Lecture lecture, int groupID, int id) {
        log.info("LectureService update method started");

        try {
            lectureDao.update(lecture, groupID, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(int id) {
        log.info("LectureService delete method started");

        try {
            lectureDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteByTeacherSurname(String surname) {
        log.info("LectureService deleteByTeacherSurname method started");

        Teacher teacher;
        try {
            teacher = teacherDao.getByTeacherSurname(surname);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        try {
            lectureDao.deleteByTeacherId(teacher.getId());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
