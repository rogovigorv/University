package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.LectureDao;
import com.foxminded.university.models.Lecture;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class LectureService {
    private final LectureDao lectureDao;

    @Autowired
    public LectureService(LectureDao lectureDao) {
        this.lectureDao = lectureDao;
    }

    public void create(Lecture lecture) {
        log.info("Create lecture: {}", lecture);

        try {
            lectureDao.create(lecture);
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

    @Transactional
    public void update(Lecture lecture) {
        log.info("Update lecture: {}", lecture.getLectureName());

        try {
            lectureDao.update(lecture);
        } catch (DaoException e) {
            log.warn("Unable to update lecture with name: {}", lecture.getLectureName());
            throw new ServiceException("Unable to update lecture with name " + lecture.getLectureName() + ".", e);
        }
    }

    @Transactional
    public void delete(int id) {
        log.info("Delete lecture with ID: {}", id);

        try {
            lectureDao.delete(id);
        } catch (DaoException e) {
            log.warn("Unable to delete lecture with ID: {}", id);
            throw new ServiceException("Unable to delete lecture with ID " + id + ".", e);
        }
    }

    public Page<Lecture> findPaginated(Pageable pageable) {
        log.debug("Get lectures pages");

        List<Lecture> lectures = lectureDao.showAll();

        lectures.sort(Comparator.comparing(Lecture::getId));

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Lecture> currentPageList;

        if (lectures.size() < startItem) {
            currentPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, lectures.size());
            currentPageList = lectures.subList(startItem, toIndex);
        }

        Page<Lecture> lecturePage =
                new PageImpl<>(currentPageList, PageRequest.of(currentPage, pageSize), lectures.size());

        return lecturePage;
    }
}
