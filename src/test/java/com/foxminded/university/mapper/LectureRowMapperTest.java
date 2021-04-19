package com.foxminded.university.mapper;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
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
public class LectureRowMapperTest {

    @Mock
    private ResultSet resultSet;

    @Mock
    private TeacherDao teacherDao;

    @Mock
    private GroupDao groupDao;

    @InjectMocks
    private LectureRowMapper lectureRowMapper;

    @Test
    void mapLectureShouldReturnLectureWithId1AndLectureNameIsMath() throws SQLException {
        Teacher teacher = new Teacher(25, "Bronislav", "Potemkin");
        Group group = new Group(3, "Geeks");

        when(resultSet.getInt("teacher_id")).thenReturn(25);
        when(teacherDao.getById(25)).thenReturn(teacher);

        when(resultSet.getInt("group_id")).thenReturn(3);
        when(groupDao.getById(3)).thenReturn(group);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("lectureName")).thenReturn("Math");
        when(resultSet.getString("description")).thenReturn("Simple math");

        Lecture expected = new Lecture(1, teacher, "Math", "Simple math", group);

        Lecture actual = lectureRowMapper.mapRow(resultSet, 1);

        assertEquals(expected, actual);

        verify(resultSet).getInt("teacher_id");
        verify(teacherDao).getById(25);
        verify(resultSet).getInt("group_id");
        verify(groupDao).getById(3);
        verify(resultSet).getInt("id");
        verify(resultSet).getString("lectureName");
        verify(resultSet).getString("description");
    }
}
