package com.foxminded.university.controller;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Teacher;
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

import java.util.ArrayList;
import java.util.List;

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
public class TeachersControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private TeacherDao teacherDaoMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        Mockito.reset(teacherDaoMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void showAll_ShouldAddTeachersToModelAndRenderPaginatedTeachersListViewAndCallTheShowAllDaoMethodOnce() throws Exception {
        Teacher firstTeacher = new Teacher(1, "Nikanor", "Ivanovich");
        Teacher secondTeacher = new Teacher(2, "Vasil", "Ivanovich");

        List<Teacher> teachers = new ArrayList<>();
        teachers.add(firstTeacher);
        teachers.add(secondTeacher);

        when(teacherDaoMock.showAll()).thenReturn(teachers);

        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/teachers"))
                .andExpect(model().attributeExists("teacherPage"))
                .andExpect(model().attribute("teacherPage",
                        hasProperty("totalElements", equalTo(2L))));

        verify(teacherDaoMock, times(1)).showAll();
        verifyNoMoreInteractions(teacherDaoMock);
    }

    @Test
    void showEach_ShouldAddTeacherToModelAndRenderTeacherEditViewAndCallTheGetByIdDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, "Pet'ka", "Petrov");

        when(teacherDaoMock.getById(1)).thenReturn(teacher);

        mockMvc.perform(get("/teachers/{id}/edit", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/edit"))
                .andExpect(model().attribute("teacherId",
                        hasProperty("firstName", is("Pet'ka"))))
                .andExpect(model().attribute("teacherId",
                        hasProperty("lastName", is("Petrov"))));

        verify(teacherDaoMock, times(1)).getById(1);
        verifyNoMoreInteractions(teacherDaoMock);
    }

    @Test
    void update_ShouldAddTeacherToModelAndRedirectToTeacherListViewAndCallTheUpdateDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, "Alexander", "Viktorov");

        doNothing().when(teacherDaoMock).update(teacher);

        mockMvc.perform(patch("/teachers/{id}/edit", 1L)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Alexander")
                .param("lastName", "Viktorov")
                .sessionAttr("teacher", teacher)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/teachers"))
                .andExpect(model().attribute("teacher",
                        hasProperty("firstName", is("Alexander"))))
                .andExpect(model().attribute("teacher",
                        hasProperty("lastName", is("Viktorov"))));

        verify(teacherDaoMock, times(1)).update(teacher);
        verifyNoMoreInteractions(teacherDaoMock);
    }

    @Test
    void addNew_ShouldAddEmptyTeacherToModelAndRenderTeacherAddView() throws Exception {
        mockMvc.perform(get("/teachers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/new"))
                .andExpect(model().attribute("newTeacher", hasProperty("firstName", nullValue())));
    }

    @Test
    void create_ShouldAddTeacherToModelAndRedirectToTeacherListViewAndCallTheCreateDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(0, "Valdemar", "Valdemarov");

        doNothing().when(teacherDaoMock).create(teacher);

        mockMvc.perform(post("/teachers/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Valdemar")
                .param("lastName", "Valdemarov")
                .sessionAttr("teacher", teacher)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/teachers"))
                .andExpect(model().attribute("teacher",
                        hasProperty("firstName", is("Valdemar"))))
                .andExpect(model().attribute("teacher",
                        hasProperty("lastName", is("Valdemarov"))));

        verify(teacherDaoMock, times(1)).create(teacher);
        verifyNoMoreInteractions(teacherDaoMock);
    }

    @Test
    void delete_ShouldRedirectToTeacherListViewAndCallTheDeleteDaoMethodOnce() throws Exception {
        final int id = 1;

        doNothing().when(teacherDaoMock).delete(id);

        mockMvc.perform(delete("/teachers/{id}", 1L))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/teachers"));

        verify(teacherDaoMock, times(1)).delete(id);
        verifyNoMoreInteractions(teacherDaoMock);
    }
}
