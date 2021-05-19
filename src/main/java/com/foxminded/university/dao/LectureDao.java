package com.foxminded.university.dao;

import com.foxminded.university.mapper.LectureRowMapper;
import com.foxminded.university.models.Lecture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.foxminded.university.dao.Queries.LECTURE_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.LECTURE_DELETE_BY_TEACHER_ID;
import static com.foxminded.university.dao.Queries.LECTURE_SELECT_BY_GROUP_ID;
import static com.foxminded.university.dao.Queries.LECTURE_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.LECTURE_CREATE;
import static com.foxminded.university.dao.Queries.LECTURE_UPDATE_BY_ID;

@Repository
@Slf4j
public class LectureDao implements UniversityDao<Lecture> {
    private final JdbcTemplate jdbcTemplate;
    private final LectureRowMapper lectureRowMapper;

    @Autowired
    public LectureDao(JdbcTemplate jdbcTemplate, LectureRowMapper lectureRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.lectureRowMapper = lectureRowMapper;
    }

    public void create(Lecture lecture, int groupID) throws DaoException {
        log.debug("Create lecture: {} and group ID: {}", lecture, groupID);

        try {
            jdbcTemplate.update(LECTURE_CREATE, lecture.getId(), lecture.getLectureName(),
                    lecture.getDescription(), lecture.getTeacher().getId(), groupID);
        } catch (DataAccessException e) {
            log.warn("Unable to create this lecture {}", lecture);
            throw new DaoException(e);
        }
    }

    @Override
    public Lecture getById(int id) throws DaoException {
        log.debug("Get lecture with ID: {}", id);

        Lecture lecture;
        try {
            lecture = jdbcTemplate.queryForObject(LECTURE_SELECT_BY_ID, lectureRowMapper, id);
        } catch (DataAccessException e) {
            log.warn("Can't get lecture with ID: {}", id);
            throw new DaoException(e);
        }

        return lecture;
    }

    public Lecture getByGroupId(int id) throws DaoException {
        log.debug("Get lecture with group ID: {}", id);

        Lecture lecture;
        try {
            lecture = jdbcTemplate.queryForObject(LECTURE_SELECT_BY_GROUP_ID, lectureRowMapper, id);
        } catch (DataAccessException e) {
            log.warn("Can't get lecture with group ID: {}", id);
            throw new DaoException(e);
        }

        return lecture;
    }

    public void update(Lecture lecture, int groupID, int id) throws DaoException {
        log.debug("Update lecture: {}, group ID: {} and ID: {}", lecture, groupID, id);

        try {
            jdbcTemplate.update(LECTURE_UPDATE_BY_ID, lecture.getLectureName(), lecture.getDescription(),
                    lecture.getTeacher().getId(), groupID, id);
        } catch (DataAccessException e) {
            log.warn("Unable to update lecture with ID: {}", id);
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.debug("Delete lecture with ID: {}", id);

        try {
            jdbcTemplate.update(LECTURE_DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            log.warn("Unable to delete lecture with ID: {}", id);
            throw new DaoException(e);
        }
    }

    public void deleteByTeacherId(int id) throws DaoException {
        log.info("Delete lecture with teacher ID: {}", id);

        try {
            jdbcTemplate.update(LECTURE_DELETE_BY_TEACHER_ID, id);
        } catch (DataAccessException e) {
            log.warn("Unable to delete lecture with teacher ID: {}", id);
            throw new DaoException(e);
        }
    }
}
