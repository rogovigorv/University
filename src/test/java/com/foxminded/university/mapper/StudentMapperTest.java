package com.foxminded.university.mapper;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentMapperTest {
    private static final Group GROUP =
            new Group(1, "Dream team");

    @Mock
    private ResultSet resultSet;

    @Mock
    private GroupDao groupDao;

    @Spy
    private Student student;

    @InjectMocks
    private StudentMapper studentMapper;

    @Test
    void mapStudentShouldReturnStudentWithId1AndLectureNameIsOleg() throws SQLException {
        when(resultSet.getInt("group_id")).thenReturn(1);
        when(groupDao.getById(1)).thenReturn(GROUP);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("firstName")).thenReturn("Oleg");
        when(resultSet.getString("lastName")).thenReturn("Silovich");

        String expected = "Student id: 1\n" +
                "First name: Oleg\n" +
                "Last name: Silovich\n" +
                "Group: \n" +
                "Group id: 1\n" +
                "Group name: Dream team";

        Student actualStudent = studentMapper.mapRow(resultSet, 1);
        String actual = null;
        if (actualStudent != null) {
            actual = actualStudent.toString();
        }

        assertEquals(expected, actual);
    }
}
