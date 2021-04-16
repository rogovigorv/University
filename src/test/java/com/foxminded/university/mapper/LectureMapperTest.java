package com.foxminded.university.mapper;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Lecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
public class LectureMapperTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";
    private static final String INSERT_TEST_DATA = "insert_test_data.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private LectureMapper lectureMapper;

    @Mock
    ResultSet resultSet;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
        sqlRunner.runScript(INSERT_TEST_DATA);
    }

    @Test
    void mapLectureShouldReturnLectureWithId1AndLectureNameIsMath() throws SQLException {
        when(resultSet.getInt("teacher_id")).thenReturn(25);

        when(resultSet.getInt("group_id")).thenReturn(3);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("lectureName")).thenReturn("Math");
        when(resultSet.getString("description")).thenReturn("Simple math");

        String expected = "Lecture id: 1\n" +
                "Teacher: \n" +
                "Teacher id: 25\n" +
                "First name: Bronislav\n" +
                "Last name: Potemkin\n" +
                "Lecture name: Math\n" +
                "Description: Simple math\n" +
                "Group: \n" +
                "Group id: 3\n" +
                "Group name: Geeks";

        Lecture lecture = lectureMapper.mapRow(resultSet, 1);
        String actual = null;
        if (lecture != null) {
            actual = lecture.toString();
        }

        assertEquals(expected, actual);
    }
}
