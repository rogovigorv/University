package com.foxminded.university.dao;

import com.foxminded.university.models.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import static com.foxminded.university.dao.Queries.TIMETABLE_CREATE;
import static com.foxminded.university.dao.Queries.TIMETABLE_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.TIMETABLE_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.TIMETABLE_UPDATE_BY_ID;

public class TimetableDao implements UniversityDao<Timetable> {
    private static final String EXCEPTION_MESSAGE = "Can't find timetable with id: ";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimetableDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Timetable timetable) {
        jdbcTemplate.update(TIMETABLE_CREATE, timetable.getId(), timetable.getStart(), timetable.getDuration());
    }

    @Override
    public Timetable getById(int id) {
        return jdbcTemplate.query(TIMETABLE_SELECT_BY_ID, new BeanPropertyRowMapper<>(Timetable.class),
                new Object[]{id}).stream().findAny().orElseThrow(() -> new ExceptionDao(EXCEPTION_MESSAGE + id));
    }

    public void update(int id, Timetable timetable) {
        jdbcTemplate.update(TIMETABLE_UPDATE_BY_ID, timetable.getStart(), timetable.getDuration(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(TIMETABLE_DELETE_BY_ID, id);
    }
}
