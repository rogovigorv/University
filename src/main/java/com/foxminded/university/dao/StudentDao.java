package com.foxminded.university.dao;

import com.foxminded.university.mapper.StudentRowMapper;
import com.foxminded.university.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.foxminded.university.dao.Queries.STUDENT_CREATE;
import static com.foxminded.university.dao.Queries.STUDENT_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.STUDENT_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.STUDENT_UPDATE_BY_ID;
import static com.foxminded.university.dao.Queries.STUDENT_UPDATE_GROUP_BY_ID;

@Repository
@Slf4j
public class StudentDao implements UniversityDao<Student>{
    private final JdbcTemplate jdbcTemplate;
    private final StudentRowMapper studentRowMapper;

    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRowMapper = studentRowMapper;
    }

    public void create(Student student) throws DaoException {
        log.debug("Create student: {}", student);

        try {
            jdbcTemplate.update(STUDENT_CREATE, student.getId(), student.getFirstName(),
                    student.getLastName(), student.getGroup().getId());
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Student getById(int id) throws DaoException {
        log.debug("Get student with ID: {}", id);

        Student student;
        try {
            student = jdbcTemplate.queryForObject(STUDENT_SELECT_BY_ID, studentRowMapper, id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }

        return student;
    }

    public void update(Student student, int id) throws DaoException {
        log.debug("Update student: {}, and ID: {}", student, id);

        try {
            jdbcTemplate.update(STUDENT_UPDATE_BY_ID, student.getFirstName(), student.getLastName(),
                    student.getGroup().getId(), id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.debug("Delete student with ID: {}", id);

        try {
            jdbcTemplate.update(STUDENT_DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

    public void updateGroup(int studentId, int groupId) throws DaoException {
        log.debug("Update student with ID: {}, and group ID: {}", studentId, groupId);

        try {
            jdbcTemplate.update(STUDENT_UPDATE_GROUP_BY_ID, groupId, studentId);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }
}
