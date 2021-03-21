package com.foxminded.university.dao;

import com.foxminded.university.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import static com.foxminded.university.dao.Queries.TEACHER_CREATE;
import static com.foxminded.university.dao.Queries.TEACHER_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.TEACHER_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.TEACHER_UPDATE_BY_ID;

public class TeacherDao implements UniversityDao<Teacher> {
    private static final String EXCEPTION_MESSAGE = "Can't find teacher with id: ";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeacherDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Teacher teacher) {
        jdbcTemplate.update(TEACHER_CREATE, teacher.getId(), teacher.getFirstName(), teacher.getLastName());
    }

    @Override
    public Teacher getById(int id) {
        return jdbcTemplate.query(TEACHER_SELECT_BY_ID, new BeanPropertyRowMapper<>(Teacher.class), new Object[]{id})
                .stream().findAny().orElseThrow(() -> new ExceptionDao(EXCEPTION_MESSAGE + id));
    }

    public void update(int id, Teacher teacher) {
        jdbcTemplate.update(TEACHER_UPDATE_BY_ID, teacher.getFirstName(), teacher.getLastName(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(TEACHER_DELETE_BY_ID, id);
    }
}
