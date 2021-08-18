package com.foxminded.university.controller;

import com.foxminded.university.models.Teacher;
import com.foxminded.university.service.TeacherService;
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
public class TeachersControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeachersController teachersController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(teachersController).build();
    }

    @Test
    void showAll_ShouldAddTeachersToModelAndRenderPaginatedTeachersListViewAndCallTheShowAllDaoMethodOnce()
            throws Exception {
        Teacher firstTeacher = new Teacher(1, "Nikanor", "Ivanovich");
        Teacher secondTeacher = new Teacher(2, "Vasil", "Ivanovich");

        int currentPage = 1;
        int pageSize = 12;

        List<Teacher> teachers = Arrays.asList(firstTeacher, secondTeacher);

        Page<Teacher> page = new PageImpl<>(teachers, PageRequest.of(0, 2), teachers.size());

        when(teacherService.findPaginated(isA(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/teachers"))
                .andExpect(model().attributeExists("teacherPage"))
                .andExpect(model().attribute("teacherPage",
                        hasProperty("totalElements", equalTo(2L))));

        verify(teacherService, times(1))
                .findPaginated(PageRequest.of(currentPage - 1, pageSize));
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void showEach_ShouldAddTeacherToModelAndRenderTeacherEditViewAndCallTheGetByIdDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, "Pet'ka", "Petrov");

        when(teacherService.getById(1)).thenReturn(teacher);

        mockMvc.perform(get("/teachers/{id}/edit", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/edit"))
                .andExpect(model().attribute("teacherId",
                        hasProperty("firstName", is("Pet'ka"))))
                .andExpect(model().attribute("teacherId",
                        hasProperty("lastName", is("Petrov"))));

        verify(teacherService, times(1)).getById(1);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void update_ShouldAddTeacherToModelAndRedirectToTeacherListViewAndCallTheUpdateDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, "Alexander", "Viktorov");

        doNothing().when(teacherService).update(teacher);

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

        verify(teacherService, times(1)).update(teacher);
        verifyNoMoreInteractions(teacherService);
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

        doNothing().when(teacherService).create(teacher);

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

        verify(teacherService, times(1)).create(teacher);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void delete_ShouldRedirectToTeacherListViewAndCallTheDeleteDaoMethodOnce() throws Exception {
        final int id = 1;

        doNothing().when(teacherService).delete(id);

        mockMvc.perform(delete("/teachers/{id}", 1L))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/teachers"));

        verify(teacherService, times(1)).delete(id);
        verifyNoMoreInteractions(teacherService);
    }
}
