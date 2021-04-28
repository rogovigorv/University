package com.foxminded.university.service;

import com.foxminded.university.dao.LectureDao;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureService {
    private final LectureDao lectureDao;
    private final TeacherDao teacherDao;

    @Autowired
    public LectureService(LectureDao lectureDao, TeacherDao teacherDao) {
        this.lectureDao = lectureDao;
        this.teacherDao = teacherDao;
    }

    public void create(Lecture lecture, int groupID) {
        lectureDao.create(lecture, groupID);
    }

    public Lecture getById(int id) {
        return lectureDao.getById(id);
    }

    public Lecture getByGroupId(int id) {
        return lectureDao.getByGroupId(id);
    }

    public void update(Lecture lecture, int groupID, int id) {
        lectureDao.update(lecture, groupID, id);
    }

    public void delete(int id) {
        lectureDao.delete(id);
    }

    public void deleteByTeacherSurname(String surname) {
        Teacher teacher = teacherDao.getByTeacherSurname(surname);
        lectureDao.deleteByTeacherId(teacher.getId());
    }
}
