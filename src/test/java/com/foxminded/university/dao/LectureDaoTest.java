package com.foxminded.university.dao;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
@Slf4j
class LectureDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private LectureDao lectureDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private GroupDao groupDao;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
    }

    @Test
    void getLectureByIdShouldReturnActualLectureWithNameMath() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherDao.create(teacher);

        Group group = new Group(1, "Geeks");
        groupDao.create(group);

        Lecture expected = new Lecture(1, teacher, "Math", "Simple math", group);
        lectureDao.create(expected);

        Lecture actual = lectureDao.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void updateLectureByIdShouldReturnActualLectureWithNameSleddingByUsingMethodGetById() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherDao.create(teacher);

        Group group = new Group(1, "Geeks");
        groupDao.create(group);

        Lecture lecture = new Lecture(1, teacher, "Math", "Simple math", group);
        lectureDao.create(lecture);

        Lecture expected = new Lecture(1, teacher, "Sledding", "Sledding drift", group);

        Lecture lectureWithDifferentName =
                new Lecture(1, teacher, "Sledding", "Sledding drift", group);
        lectureDao.update(lectureWithDifferentName);

        Lecture actual = lectureDao.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void createGroupShouldReturnActualLectureWithNameHowToTossPancakesCorrectlyByUsingMethodGetById() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherDao.create(teacher);

        Group group = new Group(1, "Geeks");
        groupDao.create(group);

        Lecture expected = new Lecture(1, teacher,
                "How to toss pancakes correctly", "Simple Tossing pancakes", group);

        Lecture newLecture = new Lecture(1, teacher,
                "How to toss pancakes correctly", "Simple Tossing pancakes", group);
        lectureDao.create(newLecture);

        Lecture actual = lectureDao.getById(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }

    @Test
    void getLectureByIdGroupIdShouldReturnActualLectureWithNameEng() {
        Teacher teacher = new Teacher(1, "Bronislav", "Potemkin");
        teacherDao.create(teacher);

        Group group = new Group(1, "Geeks");
        groupDao.create(group);

        Lecture expected = new Lecture(1, teacher, "Math", "Simple math", group);
        lectureDao.create(expected);

        Lecture actual = lectureDao.getByGroupId(1);

        assertThat(expected, samePropertyValuesAs(actual));
    }
}
