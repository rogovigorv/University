package com.foxminded.university.dao;

import com.foxminded.university.mapper.LectureMapper;
import com.foxminded.university.models.Lecture;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import static com.foxminded.university.dao.Queries.LECTURE_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.LECTURE_SELECT_BY_GROUP_ID;
import static com.foxminded.university.dao.Queries.LECTURE_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.LECTURE_CREATE;
import static com.foxminded.university.dao.Queries.LECTURE_UPDATE_BY_ID;

@Component
public class LectureDao implements UniversityDao<Lecture> {
    private static final String EXCEPTION_MESSAGE = "Can't find lecture with id: ";

    private final JdbcTemplate jdbcTemplate;

    public LectureDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Lecture lecture, int groupID) {
        jdbcTemplate.update(LECTURE_CREATE, lecture.getId(), lecture.getLectureName(),
                lecture.getDescription(), lecture.getTeacher().getId(), groupID);
    }

    @Override
    public Lecture getById(int id) {
        return jdbcTemplate.query(LECTURE_SELECT_BY_ID, new LectureMapper(), new Object[]{id})
                .stream()
                .findAny()
                .orElseThrow(() -> new ExceptionDao(EXCEPTION_MESSAGE + id));
    }

    public Lecture getByGroupId(int id) {
        return jdbcTemplate.query(LECTURE_SELECT_BY_GROUP_ID, new LectureMapper(), new Object[]{id})
                .stream()
                .findAny()
                .orElseThrow(() -> new ExceptionDao(EXCEPTION_MESSAGE + id));
    }

    public void update(Lecture lecture, int groupID, int id) {
        jdbcTemplate.update(LECTURE_UPDATE_BY_ID, lecture.getLectureName(), lecture.getDescription(),
                lecture.getTeacher().getId(), groupID, id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(LECTURE_DELETE_BY_ID, id);
    }
}
