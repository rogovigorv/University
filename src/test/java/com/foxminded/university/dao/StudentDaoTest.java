package com.foxminded.university.dao;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
@Slf4j
class StudentDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private GroupDao groupDao;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
    }

    @Test
    void getStudentByIdShouldReturnActualStudentWithNameOleg() {
        Group group = new Group(1, "Dream team");
        groupDao.create(group);

        Student expected = new Student(1, "Oleg", "Silovich", group);
        studentDao.create(expected);

        Student actual = studentDao.getById(expected.getId());

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void updateStudentByIdShouldReturnActualStudentWithNameKolyaByUsingMethodGetById() {
        Group group = new Group(1, "Dream team");
        groupDao.create(group);

        Student student = new Student(1, "Oleg", "Silovich", group);
        studentDao.create(student);

        Student expected = new Student(1, "Kolya", "Balalaikin", group);

        Student studentWithDifferentName = new Student(1, "Kolya", "Balalaikin", group);
        studentDao.update(studentWithDifferentName);

        Student actual = studentDao.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void createStudentShouldReturnActualStudentWithNameBorisByUsingMethodGetById() {
        Group group = new Group(1, "Dream team");
        groupDao.create(group);

        Student newStudent = new Student(1, "Boris", "The Blade", group);
        studentDao.create(newStudent);

        Student expected = new Student(1, "Boris", "The Blade", group);

        Student actual = studentDao.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }
}
