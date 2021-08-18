package com.foxminded.university.repository;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
@ActiveProfiles("test")
@WebAppConfiguration
@Slf4j
class StudentRepositoryTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
    }

    @Test
    void getStudentByIdShouldReturnActualStudentWithNameOleg() {
        Group group = new Group(1, "Dream team");
        groupRepository.create(group);

        Student expected = new Student(1, "Oleg", "Silovich", group);
        studentRepository.create(expected);

        Student actual = studentRepository.getById(expected.getId());

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void updateStudentByIdShouldReturnActualStudentWithNameKolyaByUsingMethodGetById() {
        Group group = new Group(1, "Dream team");
        groupRepository.create(group);

        Student student = new Student(1, "Oleg", "Silovich", group);
        studentRepository.create(student);

        Student expected = new Student(1, "Kolya", "Balalaikin", group);

        Student studentWithDifferentName = new Student(1, "Kolya", "Balalaikin", group);
        studentRepository.update(studentWithDifferentName);

        Student actual = studentRepository.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void createStudentShouldReturnActualStudentWithNameBorisByUsingMethodGetById() {
        Group group = new Group(1, "Dream team");
        groupRepository.create(group);

        Student newStudent = new Student(1, "Boris", "The Blade", group);
        studentRepository.create(newStudent);

        Student expected = new Student(1, "Boris", "The Blade", group);

        Student actual = studentRepository.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }
}
