package com.foxminded.university.mapper;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Student;
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
public class StudentMapperTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";
    private static final String INSERT_TEST_DATA = "insert_test_data.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private StudentMapper studentMapper;

    @Mock
    ResultSet resultSet;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
        sqlRunner.runScript(INSERT_TEST_DATA);
    }

    @Test
    void mapStudentShouldReturnStudentWithId1AndLectureNameIsOleg() throws SQLException {
        when(resultSet.getInt("group_id")).thenReturn(1);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("firstName")).thenReturn("Oleg");
        when(resultSet.getString("lastName")).thenReturn("Silovich");

        String expected = "Student id: 1\n" +
                "First name: Oleg\n" +
                "Last name: Silovich\n" +
                "Group: \n" +
                "Group id: 1\n" +
                "Group name: Dream team";

        Student student = studentMapper.mapRow(resultSet, 1);
        String actual = null;
        if (student != null) {
            actual = student.toString();
        }

        assertEquals(expected, actual);
    }
}
