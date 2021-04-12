package com.foxminded.university.dao;


import com.foxminded.university.models.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import static com.foxminded.university.dao.Queries.LECTURE_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.LECTURE_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.LECTURE_CREATE;
import static com.foxminded.university.dao.Queries.LECTURE_UPDATE_BY_ID;

public class LectureDao implements UniversityDao<Lecture> {
    private static final String EXCEPTION_MESSAGE = "Can't find lecture with id: ";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LectureDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Lecture lecture, int studentID) {
        jdbcTemplate.update(LECTURE_CREATE, lecture.getId(), lecture.getLectureName(),
                lecture.getDescription(), lecture.getTimeTable().getId(), lecture.getTeacher().getId(), studentID);
    }

    @Override
    public Lecture getById(int id) {
        return jdbcTemplate.query(LECTURE_SELECT_BY_ID, new LectureMapper(), new Object[]{id})
                .stream().findAny().orElseThrow(() -> new ExceptionDao(EXCEPTION_MESSAGE + id));
    }

    public void update(Lecture lecture, int studentID, int id) {
        jdbcTemplate.update(LECTURE_UPDATE_BY_ID, lecture.getLectureName(), lecture.getDescription(),
                lecture.getTimeTable().getId(), lecture.getTeacher().getId(), studentID, id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(LECTURE_DELETE_BY_ID, id);
    }
}
