package com.foxminded.university.service;

import com.foxminded.university.models.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Test
    void getGroupByIdShouldReturnActualGroupWithNameDreamTeam() {
        Group expected = new Group(1, "Dream team");
        groupService.create(expected);

        Group actual = groupService.getById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void updateGroupByIdShouldReturnActualGroupWithNameLambOfGodByUsingMethodGetById() {
        Group group = new Group(1, "Dream team");
        groupService.create(group);

        Group expected = new Group(1, "Lamb Of God");
        groupService.update(expected);

        Group actual = groupService.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void createGroupShouldReturnActualGroupWithNameFreakAngelByUsingMethodGetById() {
        Group expected = new Group(1, "Freak Angel");

        Group newGroup = new Group(1, "Freak Angel");
        groupService.create(newGroup);

        Group actual = groupService.getById(1);

        assertEquals(expected, actual);
    }
}
