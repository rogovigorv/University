package com.foxminded.university.dao;

import com.foxminded.university.mapper.TeacherRowMapper;
import com.foxminded.university.models.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.foxminded.university.dao.Queries.TEACHER_CREATE;
import static com.foxminded.university.dao.Queries.TEACHER_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.TEACHER_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.TEACHER_SELECT_BY_LAST_NAME;
import static com.foxminded.university.dao.Queries.TEACHER_UPDATE_BY_ID;

@Repository
@Slf4j
public class TeacherDao implements UniversityDao<Teacher> {
    private static final String EXCEPTION_MESSAGE = "Can't find teacher with id: ";
    private static final String TEACHER_SURNAME_EXCEPTION_MESSAGE = "Can't find teacher with surname: ";

    private final JdbcTemplate jdbcTemplate;
    private final TeacherRowMapper teacherRowMapper;

    @Autowired
    public TeacherDao(JdbcTemplate jdbcTemplate, TeacherRowMapper teacherRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.teacherRowMapper = teacherRowMapper;
    }

    public void create(Teacher teacher) {
        log.info("TeacherDao create method started");
        jdbcTemplate.update(TEACHER_CREATE, teacher.getId(), teacher.getFirstName(), teacher.getLastName());
    }

    @Override
    public Teacher getById(int id) {
        log.info("TeacherDao getById method started");
        return jdbcTemplate.query(TEACHER_SELECT_BY_ID, teacherRowMapper, new Object[]{id})
                .stream()
                .findAny()
                .orElseThrow(() -> new DaoException(EXCEPTION_MESSAGE + id));
    }

    public void update(Teacher teacher, int id) {
        log.info("TeacherDao update method started");
        jdbcTemplate.update(TEACHER_UPDATE_BY_ID, teacher.getFirstName(), teacher.getLastName(), id);
    }

    @Override
    public void delete(int id) {
        log.info("TeacherDao delete method started");
        jdbcTemplate.update(TEACHER_DELETE_BY_ID, id);
    }

    public Teacher getByTeacherSurname(String surname) {
        log.info("TeacherDao getByTeacherSurname method started");
        return jdbcTemplate.query(TEACHER_SELECT_BY_LAST_NAME, teacherRowMapper, new Object[]{surname})
                .stream()
                .findAny()
                .orElseThrow(() -> new DaoException(TEACHER_SURNAME_EXCEPTION_MESSAGE + surname));
    }
}
