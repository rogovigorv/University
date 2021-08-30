package com.foxminded.university.service;

import com.foxminded.university.mapper.LectureMapper;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.LectureDto;
import com.foxminded.university.repository.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LectureMapper mapper;

    @Autowired
    public LectureService(LectureRepository lectureRepository, LectureMapper mapper) {
        this.lectureRepository = lectureRepository;
        this.mapper = mapper;
    }

    public void create(Lecture lecture) {
        log.info("Create lecture: {}", lecture);

        try {
            lectureRepository.save(lecture);
        } catch (Throwable e) {
            log.warn("Unable to create this lecture: {}", lecture);
            throw new ServiceException("Unable to create this lecture.", e);
        }
    }

    public Lecture getById(long id) {
        log.info("Get lecture with ID: {}", id);

        Lecture lecture;
        try {
            lecture = lectureRepository.getOne(id);
        } catch (Throwable e) {
            log.warn("Can't get lecture with ID: {}", id);
            throw new ServiceException("Can't get lecture with ID " + id + ".", e);
        }

        return lecture;
    }

    public void update(Lecture lecture) {
        log.info("Update lecture: {}", lecture.getLectureName());
        LectureDto dto = lecture.convertToDto(lecture);
        try {
            Lecture lectureToUpdate = lectureRepository.getOne(dto.getId());
            mapper.updateLectureFromDto(dto, lectureToUpdate);
            lectureRepository.save(lectureToUpdate);
        } catch (Throwable e) {
            log.warn("Unable to update lecture with name: {}", lecture.getLectureName());
            throw new ServiceException("Unable to update lecture with name " + lecture.getLectureName() + ".", e);
        }
    }

    public void delete(long id) {
        log.info("Delete lecture with ID: {}", id);

        try {
            lectureRepository.deleteById(id);
        } catch (Throwable e) {
            log.warn("Unable to delete lecture with ID: {}", id);
            throw new ServiceException("Unable to delete lecture with ID " + id + ".", e);
        }
    }

    public Page<Lecture> findPaginated(Pageable pageable) {
        log.debug("Get lectures pages");

        List<Lecture> lectures;
        try {
            lectures = lectureRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } catch (Throwable e) {
            log.warn("Unable to get lectures list");
            throw new ServiceException("Unable to get lectures list.", e);
        }

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
