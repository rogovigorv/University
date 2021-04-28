package com.foxminded.university.dao;

import com.foxminded.university.mapper.StudentRowMapper;
import com.foxminded.university.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.foxminded.university.dao.Queries.STUDENT_CREATE;
import static com.foxminded.university.dao.Queries.STUDENT_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.STUDENT_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.STUDENT_UPDATE_BY_ID;
import static com.foxminded.university.dao.Queries.STUDENT_UPDATE_GROUP_BY_ID;

@Repository
public class StudentDao implements UniversityDao<Student>{
    private static final String EXCEPTION_MESSAGE = "Can't find student with id: ";

    private final JdbcTemplate jdbcTemplate;
    private final StudentRowMapper studentRowMapper;

    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRowMapper = studentRowMapper;
    }

    public void create(Student student) {
        jdbcTemplate.update(STUDENT_CREATE, student.getId(), student.getFirstName(),
                student.getLastName(), student.getGroup().getId());
    }

    @Override
    public Student getById(int id) {
        return jdbcTemplate.query(STUDENT_SELECT_BY_ID, studentRowMapper, new Object[]{id})
                .stream()
                .findAny()
                .orElseThrow(() -> new DaoException(EXCEPTION_MESSAGE + id));
    }

    public void update(Student student, int id) {
        jdbcTemplate.update(STUDENT_UPDATE_BY_ID, student.getFirstName(), student.getLastName(),
                student.getGroup().getId(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(STUDENT_DELETE_BY_ID, id);
    }

    public void updateGroup(int studentId, int groupId) {
        jdbcTemplate.update(STUDENT_UPDATE_GROUP_BY_ID, groupId, studentId);
    }
}
