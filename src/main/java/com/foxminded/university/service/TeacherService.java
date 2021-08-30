package com.foxminded.university.service;

import com.foxminded.university.mapper.TeacherMapper;
import com.foxminded.university.models.Teacher;
import com.foxminded.university.models.TeacherDto;
import com.foxminded.university.repository.TeacherRepository;
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
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper mapper;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, TeacherMapper mapper) {
        this.teacherRepository = teacherRepository;
        this.mapper = mapper;
    }

    public void create(Teacher teacher) {
        log.info("Create teacher: {}", teacher);

        try {
            teacherRepository.save(teacher);
        } catch (Throwable e) {
            log.warn("Unable to create this teacher {}", teacher);
            throw new ServiceException("Unable to create this teacher.", e);
        }
    }

    public Teacher getById(long id) {
        log.info("Get teacher with ID: {}", id);

        Teacher teacher;
        try {
            teacher = teacherRepository.getOne(id);
        } catch (Throwable e) {
            log.warn("Can't get teacher with ID: {}", id);
            throw new ServiceException("Can't get teacher with ID " + id + ".", e);
        }

        return teacher;
    }

    public void update(Teacher teacher) {
        log.info("Update teacher: {}", teacher);
        TeacherDto dto = teacher.convertToDto(teacher);
        try {
            Teacher teacherToUpdate = teacherRepository.getOne(dto.getId());
            mapper.updateTeacherFromDto(dto, teacherToUpdate);
            teacherRepository.save(teacherToUpdate);
        } catch (Throwable e) {
            log.warn("Unable to update teacher with ID: {}", teacher.getId());
            throw new ServiceException("Unable to update teacher with ID " + teacher.getId() + ".", e);
        }
    }

    public void delete(long id) {
        log.info("Delete teacher with ID: {}", id);

        try {
            teacherRepository.deleteById(id);
        } catch (Throwable e) {
            log.warn("Unable to delete teacher with ID: {}", id);
            throw new ServiceException("Unable to delete teacher with ID " + id + ".", e);
        }
    }

    public Page<Teacher> findPaginated(Pageable pageable) {
        log.debug("Get teachers pages");

        List<Teacher> teachers;
        try {
            teachers = teacherRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } catch (Throwable e) {
            log.warn("Unable to get teachers list");
            throw new ServiceException("Unable to get teachers list.", e);
        }

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
