package com.foxminded.university.dao;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
public class LectureDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";
    private static final String INSERT_TEST_DATA = "insert_test_data.sql";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private LectureDao lectureDao;

    @BeforeEach
    public void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
        sqlRunner.runScript(INSERT_TEST_DATA);
    }

    @Test
    void getLectureByIdShouldReturnActualLectureWithNameMath() {
        int lectureId = 1;

        String expected = "Math";

        Lecture actualLecture = lectureDao.getById(lectureId);
        String actual = actualLecture.getLectureName();

        assertEquals(expected, actual);
    }

    @Test
    void updateLectureByIdShouldReturnActualLectureWithNameSleddingByUsingMethodGetById() {
        int lectureId = 1;
        Teacher teacher = new Teacher(25, "Bronislav", "Potemkin");
        Group group = new Group(1, "Dream team");

        String expected = "Sledding";

        Lecture lectureWithDifferentName =
                new Lecture(lectureId, teacher, "Sledding", "Sledding drift", group);
        lectureDao.update(lectureWithDifferentName, group.getId(), lectureId);

        Lecture actualLecture = lectureDao.getById(lectureId);
        String actual = actualLecture.getLectureName();

        assertEquals(expected, actual);
    }

    @Test
    void createGroupShouldReturnActualLectureWithNameHowToTossPancakesCorrectlyByUsingMethodGetById() {
        int lectureId = 4;
        int groupId = 1;
        Teacher teacher = new Teacher(25, "Bronislav", "Potemkin");
        Group group = new Group(1, "Dream team");

        String expected = "How to toss pancakes correctly";


        Lecture newLecture = new Lecture(
                lectureId,
                teacher,
                "How to toss pancakes correctly",
                "Simple Tossing pancakes",
                group);
        lectureDao.create(newLecture, groupId);

        Lecture actualLecture = lectureDao.getById(lectureId);
        String actual = actualLecture.getLectureName();

        assertEquals(expected, actual);
    }

    @Test
    void getLectureByIdGroupIdShouldReturnActualLectureWithNameEng() {
        int groupId = 2;

        String expected = "Eng";

        Lecture actualLecture = lectureDao.getByGroupId(groupId);
        String actual = actualLecture.getLectureName();

        assertEquals(expected, actual);
    }
}
