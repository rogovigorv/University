package com.foxminded.university.repository;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
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
class LectureRepositoryTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
    }

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
