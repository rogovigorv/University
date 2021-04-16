package com.foxminded.university.mapper;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
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

    @Autowired
    private LectureMapper lectureMapper;

    @Mock
    private ResultSet resultSet;

    @Mock
    private TeacherDao teacherDao;

    @Mock
    private GroupDao groupDao;

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
