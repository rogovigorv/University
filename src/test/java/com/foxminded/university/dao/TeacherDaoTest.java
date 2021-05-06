package com.foxminded.university.dao;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
@Slf4j
public class TeacherDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private TeacherDao teacherDao;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
    }

    @Test
    void getTeacherByIdShouldReturnActualTeacherWithNameBronislav() {
        log.debug("TeacherDaoTest getTeacherByIdShouldReturnActualTeacherWithNameBronislav test started");

        Teacher expected = new Teacher(25, "Bronislav", "Potemkin");
        teacherDao.create(expected);

        Teacher actual = teacherDao.getById(25);

        assertEquals(expected, actual);

        log.debug("TeacherDaoTest getTeacherByIdShouldReturnActualTeacherWithNameBronislav test completed");
    }

    @Test
    void updateTeacherByIdShouldReturnActualTeacherWithNameSergeyByUsingMethodGetById() {
        log.debug("TeacherDaoTest updateTeacherByIdShouldReturnActualTeacherWithNameSergeyByUsingMethodGetById" +
                "test started");

        Teacher teacher = new Teacher(25, "Bronislav", "Potemkin");
        teacherDao.create(teacher);

        Teacher expected = new Teacher(25, "Sergey", "Nemchinskiy");

        teacherDao.update(expected, 25);

        Teacher actual = teacherDao.getById(25);

        assertEquals(expected, actual);

        log.debug("TeacherDaoTest updateTeacherByIdShouldReturnActualTeacherWithNameSergeyByUsingMethodGetById" +
                "test completed");
    }

    @Test
    void createTeacherShouldReturnActualTeacherWithNameIvanByUsingMethodGetById() {
        log.debug("TeacherDaoTest createTeacherShouldReturnActualTeacherWithNameIvanByUsingMethodGetById" +
                "test started");

        Teacher expected = new Teacher(20, "Ivan", "Ivanov");
        teacherDao.create(expected);

        Teacher actual = teacherDao.getById(20);

        assertEquals(expected, actual);

        log.debug("TeacherDaoTest createTeacherShouldReturnActualTeacherWithNameIvanByUsingMethodGetById" +
                "test completed");
    }
}
