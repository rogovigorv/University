package com.foxminded.university.controller;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = SpringConfigTest.class)
@WebAppConfiguration
class GroupsControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private GroupDao groupDaoMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        Mockito.reset(groupDaoMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void showAll_ShouldAddGroupsToModelAndRenderPaginatedGroupsListViewAndCallTheShowAllDaoMethodOnce() throws Exception {
        Group firstGroup = new Group(1, "Backstreet Boys");
        Group secondGroup = new Group(2, "N Sync");

        List<Group> groups = new ArrayList<>();
        groups.add(firstGroup);
        groups.add(secondGroup);

        when(groupDaoMock.showAll()).thenReturn(groups);

        mockMvc.perform(get("/groups"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups/groups"))
               .andExpect(model().attributeExists("groupPage"))
               .andExpect(model().attribute("groupPage",
                       hasProperty("totalElements", equalTo(2L))));

        verify(groupDaoMock, times(1)).showAll();
        verifyNoMoreInteractions(groupDaoMock);
    }

    @Test
    void showEach_ShouldAddGroupToModelAndRenderGroupEditViewAndCallTheGetByIdDaoMethodOnce() throws Exception {
        Group group = new Group(1, "5ive");

        when(groupDaoMock.getById(1)).thenReturn(group);

        mockMvc.perform(get("/groups/{id}/edit", 1L))
               .andExpect(status().isOk())
               .andExpect(view().name("groups/edit"))
               .andExpect(model().attribute("groupId", hasProperty("groupName", is("5ive"))));

        verify(groupDaoMock, times(1)).getById(1);
        verifyNoMoreInteractions(groupDaoMock);
    }

    @Test
    void update_ShouldAddGroupToModelAndRedirectToGroupsListViewAndCallTheUpdateDaoMethodOnce() throws Exception {
        Group group = new Group(1, "Spice Girls");

        doNothing().when(groupDaoMock).update(group);

        mockMvc.perform(patch("/groups/{id}/edit", 1L)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("groupName", "Spice Girls")
                .sessionAttr("group", group)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/groups"))
                .andExpect(model().attribute("group", hasProperty("groupName", is("Spice Girls"))));

        verify(groupDaoMock, times(1)).update(group);
        verifyNoMoreInteractions(groupDaoMock);
    }

    @Test
    void addNew_ShouldAddEmptyGroupToModelAndRenderGroupAddView() throws Exception {
        mockMvc.perform(get("/groups/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups/new"))
                .andExpect(model().attribute("newGroup", hasProperty("groupName", nullValue())));
    }

    @Test
    void create_ShouldAddGroupToModelAndRedirectToGroupsListViewAndCallTheCreateDaoMethodOnce() throws Exception {
        Group group = new Group(0, "Aqua");

        doNothing().when(groupDaoMock).create(group);

        mockMvc.perform(post("/groups/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("groupName", "Aqua")
                .sessionAttr("group", group)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/groups"))
                .andExpect(model().attribute("group", hasProperty("groupName", is("Aqua"))));

        verify(groupDaoMock, times(1)).create(group);
        verifyNoMoreInteractions(groupDaoMock);
    }

    @Test
    void delete_ShouldRedirectToGroupsListViewAndCallTheDeleteDaoMethodOnce() throws Exception {
        final int id = 1;

        doNothing().when(groupDaoMock).delete(id);

        mockMvc.perform(delete("/groups/{id}", 1L))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/groups"));

        verify(groupDaoMock, times(1)).delete(id);
        verifyNoMoreInteractions(groupDaoMock);
    }
}
