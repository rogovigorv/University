package com.foxminded.university.controller;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.models.Group;
import com.foxminded.university.service.GroupService;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class GroupsControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private GroupService groupService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        Mockito.reset(groupService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void showAllShouldAddGroupsToModelAndRenderGroupsListView() throws Exception {
        Group firstGroup = new Group(1, "Backstreet Boys");
        Group secondGroup = new Group(2, "N Sync");

        List<Group> groups = new ArrayList<>();
        groups.add(firstGroup);
        groups.add(secondGroup);

        when(groupService.findPaginated(PageRequest.of(0,12)))
                .thenReturn(new PageImpl<>(groups, PageRequest.of(0, 12), groups.size()));

        mockMvc.perform(get("/groups"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups/groups"))
               .andExpect(model().attributeExists("groupPage"))
               .andExpect(model().attribute("groupPage",
                       Matchers.hasProperty("totalElements", equalTo(2L))));

        verify(groupService, times(1)).findPaginated(PageRequest.of(0,12));
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void showEachShouldAddGroupToModelAndRenderGroupEditView() throws Exception {
        Group group = new Group(1, "5ive");

        when(groupService.getById(1)).thenReturn(group);

        mockMvc.perform(get("/groups/{id}/edit", 1L))
               .andExpect(status().isOk())
               .andExpect(view().name("groups/edit"))
               .andExpect(model().attribute("groupId", hasProperty("groupName", is("5ive"))));

        verify(groupService, times(1)).getById(1);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void addNewShouldAddGroupToModelAndRenderGroupAddView() throws Exception {
        mockMvc.perform(get("/groups/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups/new"))
               .andExpect(model().attributeExists("newGroup"));
    }
}
