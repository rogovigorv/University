package com.foxminded.university.repository;

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

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
