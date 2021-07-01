package com.foxminded.university.dao;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.generate.SqlRunner;
import com.foxminded.university.models.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
class GroupDaoTest {
    private static final String CREATE_SCRIPT = "create_university_tables.sql";

    @Autowired
    private SqlRunner sqlRunner;

    @Autowired
    private GroupDao groupDao;

    @BeforeEach
    private void setup() {
        sqlRunner.runScript(CREATE_SCRIPT);
    }

    @Test
    void getGroupByIdShouldReturnActualGroupWithNameDreamTeam() {
        Group expected = new Group(1, "Dream team");
        groupDao.create(expected);

        Group actual = groupDao.getById(expected.getId());

        assertEquals(expected,actual);
    }

    @Test
    void updateGroupByIdShouldReturnActualGroupWithNameLambOfGodByUsingMethodGetById() {
        Group group = new Group(1, "Dream team");
        groupDao.create(group);

        Group expected = new Group(1, "Lamb Of God");
        groupDao.update(expected, 1);

        Group actual = groupDao.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void createGroupShouldReturnActualGroupWithNameFreakAngelByUsingMethodGetById() {
        Group expected = new Group(1, "Freak Angel");

        Group newGroup = new Group(1, "Freak Angel");
        groupDao.create(newGroup);

        Group actual = groupDao.getById(1);

        assertEquals(expected, actual);
    }
}
