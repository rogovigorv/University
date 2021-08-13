package com.foxminded.university.controller;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.foxminded.university.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
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
        Mockito.reset(groupService);
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupsController).build();
    }

    @Test
    void showAll_ShouldAddGroupsToModelAndRenderPaginatedGroupsListViewAndCallTheShowAllDaoMethodOnce() throws Exception {
        Group firstGroup = new Group(1, "Backstreet Boys");
        Group secondGroup = new Group(2, "N Sync");

        int currentPage = 1;
        int pageSize = 2;

        List<Group> groups = Arrays.asList(firstGroup, secondGroup);

        final Page<Group> page = new PageImpl<>(groups, PageRequest.of(0, 2), groups.size());

        when(groupService.findPaginated(any(Pageable.class))).thenReturn(page);

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

//    @Test
//    void showEach_ShouldAddGroupToModelAndRenderGroupEditViewAndCallTheGetByIdDaoMethodOnce() throws Exception {
//        Group group = new Group(1, "5ive");
//
//        when(groupService.getById(1)).thenReturn(group);
//
//        mockMvc.perform(get("/groups/{id}/edit", 1L))
//               .andExpect(status().isOk())
//               .andExpect(view().name("groups/edit"))
//               .andExpect(model().attribute("groupId", hasProperty("groupName", is("5ive"))));
//
//        verify(groupService, times(1)).getById(1);
//        verifyNoMoreInteractions(groupService);
//    }
//
//    @Test
//    void update_ShouldAddGroupToModelAndRedirectToGroupsListViewAndCallTheUpdateDaoMethodOnce() throws Exception {
//        Group group = new Group(1, "Spice Girls");
//
//        doNothing().when(groupDaoMock).update(group);
//
//        mockMvc.perform(patch("/groups/{id}/edit", 1L)
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("groupName", "Spice Girls")
//                .sessionAttr("group", group)
//        )
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/groups"))
//                .andExpect(model().attribute("group", hasProperty("groupName", is("Spice Girls"))));
//
//        verify(groupDaoMock, times(1)).update(group);
//        verifyNoMoreInteractions(groupDaoMock);
//    }
//
//    @Test
//    void addNew_ShouldAddEmptyGroupToModelAndRenderGroupAddView() throws Exception {
//        mockMvc.perform(get("/groups/new"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("groups/new"))
//                .andExpect(model().attribute("newGroup", hasProperty("groupName", nullValue())));
//    }
//
//    @Test
//    void create_ShouldAddGroupToModelAndRedirectToGroupsListViewAndCallTheCreateDaoMethodOnce() throws Exception {
//        Group group = new Group(0, "Aqua");
//
//        doNothing().when(groupDaoMock).create(group);
//
//        mockMvc.perform(post("/groups/new")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("groupName", "Aqua")
//                .sessionAttr("group", group)
//        )
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/groups"))
//                .andExpect(model().attribute("group", hasProperty("groupName", is("Aqua"))));
//
//        verify(groupDaoMock, times(1)).create(group);
//        verifyNoMoreInteractions(groupDaoMock);
//    }
//
//    @Test
//    void delete_ShouldRedirectToGroupsListViewAndCallTheDeleteDaoMethodOnce() throws Exception {
//        final int id = 1;
//
//        doNothing().when(groupDaoMock).delete(id);
//
//        mockMvc.perform(delete("/groups/{id}", 1L))
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/groups"));
//
//        verify(groupDaoMock, times(1)).delete(id);
//        verifyNoMoreInteractions(groupDaoMock);
//    }
}
