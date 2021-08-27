package com.foxminded.university.service;

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GroupService groupService;

    @Test
    void getStudentByIdShouldReturnActualStudentWithNameOleg() {
        Group group = new Group(1, "Dream team");
        groupService.create(group);

        Student expected = new Student(1, "Oleg", "Silovich", group);
        studentService.create(expected);

        Student actual = studentService.getById(expected.getId());

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void updateStudentByIdShouldReturnActualStudentWithNameKolyaByUsingMethodGetById() {
        Group group = new Group(1, "Dream team");
        groupService.create(group);

        Student student = new Student(1, "Oleg", "Silovich", group);
        studentService.create(student);

        Student expected = new Student(1, "Kolya", "Balalaikin", group);

        Student studentWithDifferentName = new Student(1, "Kolya", "Balalaikin", group);
        studentService.update(studentWithDifferentName);

        Student actual = studentService.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void createStudentShouldReturnActualStudentWithNameBorisByUsingMethodGetById() {
        Group group = new Group(1, "Dream team");
        groupService.create(group);

        Student newStudent = new Student(1, "Boris", "The Blade", group);
        studentService.create(newStudent);

        Student expected = new Student(1, "Boris", "The Blade", group);

        Student actual = studentService.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }
}
