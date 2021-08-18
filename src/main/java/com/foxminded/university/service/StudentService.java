package com.foxminded.university.service;

import com.foxminded.university.models.Student;
import com.foxminded.university.repository.RepositoryException;
import com.foxminded.university.repository.StudentRepository;
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
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void create(Student student) {
        log.info("Create student: {}", student);

        try {
            studentRepository.create(student);
        } catch (RepositoryException e) {
            log.warn("Unable to create this student {}", student);
            throw new ServiceException("Unable to create this student.", e);
        }
    }

    public Student getById(int id) {
        log.info("Get student with ID: {}", id);

        Student student;
        try {
            student = studentRepository.getById(id);
        } catch (RepositoryException e) {
            log.warn("Can't get student with ID: {}", id);
            throw new ServiceException("Can't get student with ID " + id + ".", e);
        }

        return student;
    }

    public void update(Student student) {
        log.info("Update student: {}", student);

        try {
            studentRepository.update(student);
        } catch (RepositoryException e) {
            log.warn("Unable to update student with ID: {}", student.getId());
            throw new ServiceException("Unable to update student with ID " + student.getId() + ".", e);
        }
    }

    public void delete(int id) {
        log.info("Delete student with ID: {}", id);

        try {
            studentRepository.delete(id);
        } catch (RepositoryException e) {
            log.warn("Unable to delete student with ID: {}", id);
            throw new ServiceException("Unable to delete student with ID " + id + ".", e);
        }
    }

    public Page<Student> findPaginated(Pageable pageable) {
        log.debug("Get students pages");

        List<Student> students = studentRepository.showAll();

        students.sort(Comparator.comparing(Student::getId));

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
