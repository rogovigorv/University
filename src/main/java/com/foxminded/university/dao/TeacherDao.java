package com.foxminded.university.dao;

import com.foxminded.university.mapper.TeacherRowMapper;
import com.foxminded.university.models.Teacher;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.foxminded.university.dao.Queries.TEACHER_CREATE;
import static com.foxminded.university.dao.Queries.TEACHER_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.TEACHER_SELECT_ALL;
import static com.foxminded.university.dao.Queries.TEACHER_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.TEACHER_SELECT_BY_LAST_NAME;
import static com.foxminded.university.dao.Queries.TEACHER_UPDATE_BY_ID;

@Repository
@Slf4j
public class TeacherDao implements UniversityDao<Teacher> {
    private final JdbcTemplate jdbcTemplate;
    private final TeacherRowMapper teacherRowMapper;

    @Autowired
    public TeacherDao(JdbcTemplate jdbcTemplate, TeacherRowMapper teacherRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.teacherRowMapper = teacherRowMapper;
    }

    public void create(Teacher teacher) throws DaoException {
        log.debug("Create teacher: {}", teacher);

        try {
            jdbcTemplate.update(TEACHER_CREATE, teacher.getFirstName(), teacher.getLastName());
        } catch (DataAccessException e) {
            log.warn("Unable to create this teacher {}", teacher);
            throw new DaoException(e);
        }
    }

    @Override
    public Teacher getById(int id) throws DaoException {
        log.debug("Get teacher with ID: {}", id);

        Teacher teacher;
        try {
            teacher = jdbcTemplate.queryForObject(TEACHER_SELECT_BY_ID, teacherRowMapper, id);
        } catch (DataAccessException e) {
            log.warn("Can't get teacher with ID: {}", id);
            throw new DaoException(e);
        }

        return teacher;
    }

    public void update(Teacher teacher) throws DaoException {
        log.debug("Update teacher: {}", teacher);

        try {
            jdbcTemplate.update(TEACHER_UPDATE_BY_ID, teacher.getFirstName(), teacher.getLastName(), teacher.getId());
        } catch (DataAccessException e) {
            log.warn("Unable to update teacher with ID: {}", teacher.getId());
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.debug("Delete teacher with ID: {}", id);

        try {
            jdbcTemplate.update(TEACHER_DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            log.warn("Unable to delete teacher with ID: {}", id);
            throw new DaoException(e);
        }
    }

    public Teacher getByTeacherSurname(String surname) throws DaoException {
        log.debug("Get teacher by surname: {}", surname);

        Teacher teacher;
        try {
            teacher = jdbcTemplate.queryForObject(TEACHER_SELECT_BY_LAST_NAME, teacherRowMapper, surname);
        } catch (DataAccessException e) {
            log.warn("Unable to get teacher with surname {}", surname);
            throw new DaoException(e);
        }

        return teacher;
    }

    public List<Teacher> showAll() {
        log.debug("Get all teachers");

        List<Teacher> teachers;
        try {
            teachers = jdbcTemplate.query(TEACHER_SELECT_ALL, teacherRowMapper);
        } catch (DataAccessException e) {
            log.warn("Unable to get all teachers");
            throw new DaoException(e);
        }

        return teachers;
    }
}
