package com.foxminded.university.service;

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
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
class LectureServiceTest {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private GroupService groupService;

    @Test
    void getLectureByIdShouldReturnActualLectureWithNameMath() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherService.create(teacher);

        Group group = new Group(1, "Geeks");
        groupService.create(group);

        Lecture expected = new Lecture(1, teacher, "Math", "Simple math", group);
        lectureService.create(expected);

        Lecture actual = lectureService.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void updateLectureByIdShouldReturnActualLectureWithNameSleddingByUsingMethodGetById() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherService.create(teacher);

        Group group = new Group(1, "Geeks");
        groupService.create(group);

        Lecture lecture = new Lecture(1, teacher, "Math", "Simple math", group);
        lectureService.create(lecture);

        Lecture expected = new Lecture(1, teacher, "Sledding", "Sledding drift", group);

        Lecture lectureWithDifferentName =
                new Lecture(1, teacher, "Sledding", "Sledding drift", group);
        lectureService.update(lectureWithDifferentName);

        Lecture actual = lectureService.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void createGroupShouldReturnActualLectureWithNameHowToTossPancakesCorrectlyByUsingMethodGetById() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherService.create(teacher);

        Group group = new Group(1, "Geeks");
        groupService.create(group);

        Lecture expected = new Lecture(1, teacher,
                "How to toss pancakes correctly", "Simple Tossing pancakes", group);

        Lecture newLecture = new Lecture(1, teacher,
                "How to toss pancakes correctly", "Simple Tossing pancakes", group);
        lectureService.create(newLecture);

        Lecture actual = lectureService.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }
}
