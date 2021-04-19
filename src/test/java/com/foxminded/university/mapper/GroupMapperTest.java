package com.foxminded.university.mapper;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.models.Group;
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
public class GroupMapperTest {

    @Autowired
    private GroupMapper groupMapper;

    @Mock
    private ResultSet resultSet;

    @Test
    void mapGroupShouldReturnGroupWithId1AndGroupNameIsDreamTeam() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("groupName")).thenReturn("Dream team");

        String expected = "Group id: 1" + "\n" +
        "Group name: Dream team";

        Group actualGroup = groupMapper.mapRow(resultSet, 1);
        String actual = null;
        if (actualGroup != null) {
            actual = actualGroup.toString();
        }

        assertEquals(expected, actual);

        verify(resultSet).getInt("id");
        verify(resultSet).getString("groupName");
    }
}
