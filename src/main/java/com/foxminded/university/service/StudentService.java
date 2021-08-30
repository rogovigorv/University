package com.foxminded.university.service;

import com.foxminded.university.mapper.StudentMapper;
import com.foxminded.university.models.Student;
import com.foxminded.university.models.StudentDto;
import com.foxminded.university.repository.StudentRepository;
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
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper mapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public void create(Student student) {
        log.info("Create student: {}", student);

        try {
            studentRepository.save(student);
        } catch (Throwable e) {
            log.warn("Unable to create this student {}", student);
            throw new ServiceException("Unable to create this student.", e);
        }
    }

    public Student getById(long id) {
        log.info("Get student with ID: {}", id);

        Student student;
        try {
            student = studentRepository.getOne(id);
        } catch (Throwable e) {
            log.warn("Can't get student with ID: {}", id);
            throw new ServiceException("Can't get student with ID " + id + ".", e);
        }

        return student;
    }

    public void update(Student student) {
        log.info("Update student: {}", student);
        StudentDto dto = student.convertToDto(student);
        try {
            Student studentToUpdate = studentRepository.getOne(dto.getId());
            mapper.updateStudentFromDto(dto, studentToUpdate);
            studentRepository.save(studentToUpdate);
        } catch (Throwable e) {
            log.warn("Unable to update student with ID: {}", student.getId());
            throw new ServiceException("Unable to update student with ID " + student.getId() + ".", e);
        }
    }

    public void delete(long id) {
        log.info("Delete student with ID: {}", id);

        try {
            studentRepository.deleteById(id);
        } catch (Throwable e) {
            log.warn("Unable to delete student with ID: {}", id);
            throw new ServiceException("Unable to delete student with ID " + id + ".", e);
        }
    }

    public Page<Student> findPaginated(Pageable pageable) {
        log.debug("Get students pages");

        List<Student> students;
        try {
            students = studentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } catch (Throwable e) {
            log.warn("Unable to get students list");
            throw new ServiceException("Unable to get students list.", e);
        }

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Student> currentPageList;

        if (students.size() < startItem) {
            currentPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, students.size());
            currentPageList = students.subList(startItem, toIndex);
        }

        Page<Student> studentPage =
                new PageImpl<>(currentPageList, PageRequest.of(currentPage, pageSize), students.size());

        return studentPage;
    }
}
