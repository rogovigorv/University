package com.foxminded.university.dao;

import com.foxminded.university.mapper.TeacherRowMapper;
import com.foxminded.university.models.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        log.debug("TeacherDao create method started with teacher: {}", teacher);

        try {
            jdbcTemplate.update(TEACHER_CREATE, teacher.getId(), teacher.getFirstName(), teacher.getLastName());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Teacher getById(int id) throws DaoException {
        log.debug("TeacherDao getById method started with ID: {}", id);

        return jdbcTemplate.query(TEACHER_SELECT_BY_ID, teacherRowMapper, new Object[]{id})
                .stream()
                .findAny()
                .orElseThrow(() -> new DaoException(EXCEPTION_MESSAGE + id));
    }

    public void update(Teacher teacher, int id) {
        log.debug("TeacherDao update method started with teacher: {}, and ID: {}", teacher, id);

        try {
            jdbcTemplate.update(TEACHER_UPDATE_BY_ID, teacher.getFirstName(), teacher.getLastName(), id);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        log.debug("TeacherDao delete method started with ID: {}", id);

        try {
            jdbcTemplate.update(TEACHER_DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Teacher getByTeacherSurname(String surname) {
        log.debug("TeacherDao getByTeacherSurname method started with teacher surname: {}", surname);

        return jdbcTemplate.query(TEACHER_SELECT_BY_LAST_NAME, teacherRowMapper, new Object[]{surname})
                .stream()
                .findAny()
                .orElseThrow(() -> new DaoException(TEACHER_SURNAME_EXCEPTION_MESSAGE + surname));
    }
}
