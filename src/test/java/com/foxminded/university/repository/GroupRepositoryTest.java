package com.foxminded.university.repository;

import com.foxminded.university.models.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void getGroupByIdShouldReturnActualGroupWithNameDreamTeam() {
        Group expected = new Group(1, "Dream team");
        groupRepository.create(expected);

        Group actual = groupRepository.getById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void updateGroupByIdShouldReturnActualGroupWithNameLambOfGodByUsingMethodGetById() {
        Group group = new Group(1, "Dream team");
        groupRepository.create(group);

        Group expected = new Group(1, "Lamb Of God");
        groupRepository.update(expected);

        Group actual = groupRepository.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    void createGroupShouldReturnActualGroupWithNameFreakAngelByUsingMethodGetById() {
        Group expected = new Group(1, "Freak Angel");

        Group newGroup = new Group(1, "Freak Angel");
        groupRepository.create(newGroup);

        Group actual = groupRepository.getById(1);

        assertEquals(expected, actual);
    }
}
