package com.foxminded.university.mapper;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.models.Teacher;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
public class TeacherRowMapperTest {

    @Autowired
    private TeacherRowMapper teacherRowMapper;

    @Mock
    private ResultSet resultSet;

    @Test
    void mapTeacherShouldReturnTeacherWithId25FirstNameIsBronislavAndLastNameIsPotemkin() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(25);
        when(resultSet.getString("firstName")).thenReturn("Bronislav");
        when(resultSet.getString("lastName")).thenReturn("Potemkin");

        Teacher expected = new Teacher(25, "Bronislav", "Potemkin");

        Teacher actual = teacherRowMapper.mapRow(resultSet, 1);

        assertEquals(expected, actual);

        verify(resultSet).getInt("id");
        verify(resultSet).getString("firstName");
        verify(resultSet).getString("lastName");
    }
}
