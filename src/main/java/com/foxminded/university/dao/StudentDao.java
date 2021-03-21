package com.foxminded.university.dao;

import com.foxminded.university.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import static com.foxminded.university.dao.Queries.STUDENT_CREATE;
import static com.foxminded.university.dao.Queries.STUDENT_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.STUDENT_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.STUDENT_UPDATE_BY_ID;

public class StudentDao implements UniversityDao<Student>{
    private static final String EXCEPTION_MESSAGE = "Can't find student with id: ";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Student student) {
        jdbcTemplate.update(STUDENT_CREATE, student.getId(), student.getFirstName(),
                student.getLastName(), student.getGroup().getId());
    }

    @Override
    public Student getById(int id) {
        return jdbcTemplate.query(STUDENT_SELECT_BY_ID, new StudentMapper(), new Object[]{id})
                .stream().findAny().orElseThrow(() -> new ExceptionDao(EXCEPTION_MESSAGE + id));
    }

    public void update(int id, Student student) {
        jdbcTemplate.update(STUDENT_UPDATE_BY_ID, student.getFirstName(), student.getLastName(),
                student.getGroup().getId(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(STUDENT_DELETE_BY_ID, id);
    }
}
