package com.foxminded.university.dao;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Group;
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
public class GroupDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";
    private static final String INSERT_TEST_DATA = "insert_test_data.sql";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private GroupDao groupDao;

    @BeforeEach
    public void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
        sqlRunner.runScript(INSERT_TEST_DATA);
    }

    @Test
    void getGroupByIdShouldReturnActualGroupWithNameDreamTeam() {
        int groupId = 1;

        String expected = "Dream team";

        Group actualGroup = groupDao.getById(groupId);
        String actual = actualGroup.getGroupName();

        assertEquals(expected, actual);
    }

    @Test
    void updateGroupByIdShouldReturnActualGroupWithNameLambOfGodByUsingMethodGetById() {
        int groupId = 1;

        String expected = "Lamb Of God";

        Group groupWithDifferentName = new Group(groupId, "Lamb Of God");
        groupDao.update(groupWithDifferentName, groupId);

        Group actualGroup = groupDao.getById(groupId);
        String actual = actualGroup.getGroupName();

        assertEquals(expected, actual);
    }

    @Test
    void createGroupShouldReturnActualGroupWithNameFreakAngelByUsingMethodGetById() {
        int groupId = 4;

        String expected = "Freak Angel";

        Group newGroup = new Group(groupId, "Freak Angel");
        groupDao.create(newGroup);

        Group actualGroup = groupDao.getById(groupId);
        String actual = actualGroup.getGroupName();

        assertEquals(expected, actual);
    }
}
