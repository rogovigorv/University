package com.foxminded.university.controller;

import com.foxminded.university.models.Group;
import com.foxminded.university.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.isA;
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

@ExtendWith(MockitoExtension.class)
class GroupsControllerTest {
    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupsController groupsController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupsController).build();
    }

    @Test
    void showAll_ShouldAddGroupsToModelAndRenderPaginatedGroupsListViewAndCallTheShowAllDaoMethodOnce()
            throws Exception {
        Group firstGroup = new Group(1, "Backstreet Boys");
        Group secondGroup = new Group(2, "N Sync");

        int currentPage = 1;
        int pageSize = 12;

        List<Group> groups = Arrays.asList(firstGroup, secondGroup);

        Page<Group> page = new PageImpl<>(groups, PageRequest.of(0, 2), groups.size());

        when(groupService.findPaginated(isA(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/groups"))
               .andExpect(status().isOk())
               .andExpect(view().name("groups/groups"))
               .andExpect(model().attributeExists("groupPage"))
               .andExpect(model().attribute("groupPage",
                       hasProperty("totalElements", equalTo(2L))));

        verify(groupService, times(1))
                .findPaginated(PageRequest.of(currentPage - 1, pageSize));
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void showEach_ShouldAddGroupToModelAndRenderGroupEditViewAndCallTheGetByIdDaoMethodOnce() throws Exception {
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
    void update_ShouldAddGroupToModelAndRedirectToGroupsListViewAndCallTheUpdateDaoMethodOnce() throws Exception {
        Group group = new Group(1, "Spice Girls");

        doNothing().when(groupService).update(group);

        mockMvc.perform(patch("/groups/{id}/edit", 1L)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("groupName", "Spice Girls")
                .sessionAttr("group", group)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/groups"))
                .andExpect(model().attribute("group", hasProperty("groupName", is("Spice Girls"))));

        verify(groupService, times(1)).update(group);
        verifyNoMoreInteractions(groupService);
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

        doNothing().when(groupService).create(group);

        mockMvc.perform(post("/groups/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("groupName", "Aqua")
                .sessionAttr("group", group)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/groups"))
                .andExpect(model().attribute("group", hasProperty("groupName", is("Aqua"))));

        verify(groupService, times(1)).create(group);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void delete_ShouldRedirectToGroupsListViewAndCallTheDeleteDaoMethodOnce() throws Exception {
        final int id = 1;

        doNothing().when(groupService).delete(id);

        mockMvc.perform(delete("/groups/{id}", 1L))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/groups"));

        verify(groupService, times(1)).delete(id);
        verifyNoMoreInteractions(groupService);
    }
}
