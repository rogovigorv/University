package com.foxminded.university.repository;

import com.foxminded.university.models.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void getTeacherByIdShouldReturnActualTeacherWithNameBronislav() {
        Teacher expected = new Teacher(1, "Bronislav", "Potemkin");
        teacherRepository.create(expected);

        Teacher actual = teacherRepository.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void updateTeacherByIdShouldReturnActualTeacherWithNameSergeyByUsingMethodGetById() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherRepository.create(teacher);

        Teacher expected = new Teacher(1, "Sergey", "Nemchinskiy");

        teacherRepository.update(expected);

        Teacher actual = teacherRepository.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void createTeacherShouldReturnActualTeacherWithNameIvanByUsingMethodGetById() {
        Teacher expected = new Teacher(1, "Ivan", "Ivanov");
        teacherRepository.create(expected);

        Teacher actual = teacherRepository.getById(1);

        assertEquals(expected, actual);
    }
}
