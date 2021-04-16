package com.foxminded.university.dao;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
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
public class StudentDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";
    private static final String INSERT_TEST_DATA = "insert_test_data.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
        sqlRunner.runScript(INSERT_TEST_DATA);
    }

    @Test
    void getStudentByIdShouldReturnActualStudentWithNameOleg() {
        int studentId = 1;

        String expected = "Oleg";

        Student actualStudent = studentDao.getById(studentId);
        String actual = actualStudent.getFirstName();

        assertEquals(expected, actual);
    }

    @Test
    void updateStudentByIdShouldReturnActualStudentWithNameKolyaByUsingMethodGetById() {
        int studentId = 1;
        Group group = new Group(1, "Dream team");

        String expected = "Kolya";

        Student studentWithDifferentName = new Student(studentId, "Kolya", "Balalaikin", group);
        studentDao.update(studentWithDifferentName, studentId);

        Student actualStudent = studentDao.getById(studentId);
        String actual = actualStudent.getFirstName();

        assertEquals(expected, actual);
    }

    @Test
    void createStudentShouldReturnActualStudentWithNameBorisByUsingMethodGetById() {
        int studentId = 4;
        Group group = new Group(1, "Dream team");

        String expected = "Boris";

        Student newStudent = new Student(studentId, "Boris", "The Blade", group);
        studentDao.create(newStudent);

        Student actualStudent = studentDao.getById(studentId);
        String actual = actualStudent.getFirstName();

        assertEquals(expected, actual);
    }
}
