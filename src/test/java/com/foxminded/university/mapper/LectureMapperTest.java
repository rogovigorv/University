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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LectureMapperTest {
    private static final Teacher TEACHER =
            new Teacher(25, "Bronislav", "Potemkin");
    private static final Group GROUP =
            new Group(3, "Geeks");

    @Mock
    private ResultSet resultSet;

    @Mock
    private TeacherDao teacherDao;

    @Mock
    private GroupDao groupDao;

    @Spy
    private Lecture lecture;

    @InjectMocks
    private LectureMapper lectureMapper;

    @Test
    void mapLectureShouldReturnLectureWithId1AndLectureNameIsMath() throws SQLException {
        when(resultSet.getInt("teacher_id")).thenReturn(25);
        when(teacherDao.getById(25)).thenReturn(TEACHER);

        when(resultSet.getInt("group_id")).thenReturn(3);
        when(groupDao.getById(3)).thenReturn(GROUP);

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

        Lecture actualLecture = lectureMapper.mapRow(resultSet, 1);
        String actual = null;
        if (actualLecture != null) {
            actual = actualLecture.toString();
        }

        assertEquals(expected, actual);
    }
}
