package com.foxminded.university.service;

import com.foxminded.university.dao.LectureDao;
import com.foxminded.university.models.Lecture;
import org.springframework.beans.factory.annotation.Autowired;

public class LectureService {
    private final LectureDao lectureDao;

    @Autowired
    public LectureService(LectureDao lectureDao) {
        this.lectureDao = lectureDao;
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
}
