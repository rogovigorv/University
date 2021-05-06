package com.foxminded.university.mapper;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class StudentRowMapperTest {

    @Mock
    private ResultSet resultSet;

    @Mock
    private GroupDao groupDao;

    @InjectMocks
    private StudentRowMapper studentRowMapper;

    @Test
    void mapStudentShouldReturnStudentWithId1AndLectureNameIsOleg() throws SQLException {
        Group group = new Group(1, "Dream team");

        when(resultSet.getInt("group_id")).thenReturn(1);
        when(groupDao.getById(1)).thenReturn(group);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("firstName")).thenReturn("Oleg");
        when(resultSet.getString("lastName")).thenReturn("Silovich");

        Student expected = new Student(1, "Oleg", "Silovich", group);

        Student actual = studentRowMapper.mapRow(resultSet, 1);

        assertEquals(expected, actual);

        verify(resultSet).getInt("group_id");
        verify(groupDao).getById(1);
        verify(resultSet).getInt("id");
        verify(resultSet).getString("firstName");
        verify(resultSet).getString("lastName");
    }
}
