package com.foxminded.university.service;

import com.foxminded.university.models.Lecture;
import com.foxminded.university.repository.LectureRepository;
import com.foxminded.university.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class LectureService {
    private final LectureRepository lectureRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public void create(Lecture lecture) {
        log.info("Create lecture: {}", lecture);

        try {
            lectureRepository.create(lecture);
        } catch (RepositoryException e) {
            log.warn("Unable to create this lecture: {}", lecture);
            throw new ServiceException("Unable to create this lecture.", e);
        }
    }

    public Lecture getById(int id) {
        log.info("Get lecture with ID: {}", id);

        Lecture lecture;
        try {
            lecture = lectureRepository.getById(id);
        } catch (RepositoryException e) {
            log.warn("Can't get lecture with ID: {}", id);
            throw new ServiceException("Can't get lecture with ID " + id + ".", e);
        }

        return lecture;
    }

    public void update(Lecture lecture) {
        log.info("Update lecture: {}", lecture.getLectureName());

        try {
            lectureRepository.update(lecture);
        } catch (RepositoryException e) {
            log.warn("Unable to update lecture with name: {}", lecture.getLectureName());
            throw new ServiceException("Unable to update lecture with name " + lecture.getLectureName() + ".", e);
        }
    }

    public void delete(int id) {
        log.info("Delete lecture with ID: {}", id);

        try {
            lectureRepository.delete(id);
        } catch (RepositoryException e) {
            log.warn("Unable to delete lecture with ID: {}", id);
            throw new ServiceException("Unable to delete lecture with ID " + id + ".", e);
        }
    }

    public Page<Lecture> findPaginated(Pageable pageable) {
        log.debug("Get lectures pages");

        List<Lecture> lectures = lectureRepository.showAll();

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
