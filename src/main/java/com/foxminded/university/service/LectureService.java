package com.foxminded.university.service;

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
        lectureDao.create(lecture, groupID);
    }

    public Lecture getById(int id) {
        log.info("LectureService getById method started");
        return lectureDao.getById(id);
    }

    public Lecture getByGroupId(int id) {
        log.info("LectureService getByGroupId method started");
        return lectureDao.getByGroupId(id);
    }

    public void update(Lecture lecture, int groupID, int id) {
        log.info("LectureService update method started");
        lectureDao.update(lecture, groupID, id);
    }

    public void delete(int id) {
        log.info("LectureService delete method started");
        lectureDao.delete(id);
    }

    public void deleteByTeacherSurname(String surname) {
        log.info("LectureService deleteByTeacherSurname method started");

        Teacher teacher = teacherDao.getByTeacherSurname(surname);
        lectureDao.deleteByTeacherId(teacher.getId());
    }
}
