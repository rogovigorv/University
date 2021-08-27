package com.foxminded.university.service;

import com.foxminded.university.models.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @Test
    void getTeacherByIdShouldReturnActualTeacherWithNameBronislav() {
        Teacher expected = new Teacher(1, "Bronislav", "Potemkin");
        teacherService.create(expected);

        Teacher actual = teacherService.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void updateTeacherByIdShouldReturnActualTeacherWithNameSergeyByUsingMethodGetById() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherService.create(teacher);

        Teacher expected = new Teacher(1, "Sergey", "Nemchinskiy");

        teacherService.update(expected);

        Teacher actual = teacherService.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void createTeacherShouldReturnActualTeacherWithNameIvanByUsingMethodGetById() {
        Teacher expected = new Teacher(1, "Ivan", "Ivanov");
        teacherService.create(expected);

        Teacher actual = teacherService.getById(1);

        assertEquals(expected, actual);
    }
}
