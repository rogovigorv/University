package com.foxminded.university.dao;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
public class TeacherDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";
    private static final String INSERT_TEST_DATA = "insert_test_data.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private TeacherDao teacherDao;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
        sqlRunner.runScript(INSERT_TEST_DATA);
    }

    @Test
    void getTeacherByIdShouldReturnActualTeacherWithNameBronislav() {
        int teacherId = 25;

        String expected = "Bronislav";

        Teacher actualTeacher = teacherDao.getById(teacherId);
        String actual = actualTeacher.getFirstName();

        assertEquals(expected, actual);
    }

    @Test
    void updateTeacherByIdShouldReturnActualTeacherWithNameSergeyByUsingMethodGetById() {
        int teacherId = 25;

        String expected = "Sergey";

        Teacher teacherWithDifferentName = new Teacher(teacherId, "Sergey", "Nemchinskiy");
        teacherDao.update(teacherWithDifferentName, teacherId);

        Teacher actualTeacher = teacherDao.getById(teacherId);
        String actual = actualTeacher.getFirstName();

        assertEquals(expected, actual);
    }

    @Test
    void createTeacherShouldReturnActualTeacherWithNameIvanByUsingMethodGetById() {
        int teacherId = 20;

        String expected = "Ivan";

        Teacher teacherWithDifferentName = new Teacher(teacherId, "Ivan", "Ivanov");
        teacherDao.create(teacherWithDifferentName);

        Teacher actualTeacher = teacherDao.getById(teacherId);
        String actual = actualTeacher.getFirstName();

        assertEquals(expected, actual);
    }
}
