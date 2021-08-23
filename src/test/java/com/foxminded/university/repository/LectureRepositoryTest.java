package com.foxminded.university.repository;

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LectureRepositoryTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void getLectureByIdShouldReturnActualLectureWithNameMath() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherRepository.create(teacher);

        Group group = new Group(1, "Geeks");
        groupRepository.create(group);

        Lecture expected = new Lecture(1, teacher, "Math", "Simple math", group);
        lectureRepository.create(expected);

        Lecture actual = lectureRepository.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void updateLectureByIdShouldReturnActualLectureWithNameSleddingByUsingMethodGetById() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherRepository.create(teacher);

        Group group = new Group(1, "Geeks");
        groupRepository.create(group);

        Lecture lecture = new Lecture(1, teacher, "Math", "Simple math", group);
        lectureRepository.create(lecture);

        Lecture expected = new Lecture(1, teacher, "Sledding", "Sledding drift", group);

        Lecture lectureWithDifferentName =
                new Lecture(1, teacher, "Sledding", "Sledding drift", group);
        lectureRepository.update(lectureWithDifferentName);

        Lecture actual = lectureRepository.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void createGroupShouldReturnActualLectureWithNameHowToTossPancakesCorrectlyByUsingMethodGetById() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherRepository.create(teacher);

        Group group = new Group(1, "Geeks");
        groupRepository.create(group);

        Lecture expected = new Lecture(1, teacher,
                "How to toss pancakes correctly", "Simple Tossing pancakes", group);

        Lecture newLecture = new Lecture(1, teacher,
                "How to toss pancakes correctly", "Simple Tossing pancakes", group);
        lectureRepository.create(newLecture);

        Lecture actual = lectureRepository.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }
}
