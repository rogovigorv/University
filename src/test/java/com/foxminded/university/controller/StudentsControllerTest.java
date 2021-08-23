package com.foxminded.university.controller;

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import com.foxminded.university.service.StudentService;
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
public class StudentsControllerTest {
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentsController studentsController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentsController).build();
    }

    @Test
    void showAll_ShouldAddDStudentsToModelAndRenderPaginatedStudentsListViewAndCallTheShowAllDaoMethodOnce()
            throws Exception {
        Group group = new Group(1, "Backstreet Boys");
        Student firstStudent =
                new Student(1,  "Kirill", "Kirillov", group);
        Student secondStudent =
                new Student(2,  "Artem", "Artemov", group);

        int currentPage = 1;
        int pageSize = 12;

        List<Student> students = Arrays.asList(firstStudent, secondStudent);

        Page<Student> page = new PageImpl<>(students, PageRequest.of(0, 2), students.size());

        when(studentService.findPaginated(isA(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/students"))
                .andExpect(model().attributeExists("studentPage"))
                .andExpect(model().attribute("studentPage",
                        hasProperty("totalElements", equalTo(2L))));

        verify(studentService, times(1))
                .findPaginated(PageRequest.of(currentPage - 1, pageSize));
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void showEach_ShouldAddStudentToModelAndRenderStudentEditViewAndCallTheGetByIdDaoMethodOnce() throws Exception {
        Group group = new Group(1, "N Sync");
        Student student = new Student(1,  "Roman", "Romanov", group);

        when(studentService.getById(1)).thenReturn(student);

        mockMvc.perform(get("/students/{id}/edit", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("students/edit"))
                .andExpect(model().attribute("studentId",
                        hasProperty("firstName", is("Roman"))))
                .andExpect(model().attribute("studentId",
                        hasProperty("lastName", is("Romanov"))))
                .andExpect(model().attribute("studentId",
                        hasProperty("group", is(group))));

        verify(studentService, times(1)).getById(1);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void update_ShouldAddStudentToModelAndRedirectToStudentsListViewAndCallTheUpdateDaoMethodOnce() throws Exception {
        Group group = new Group(1, "5ive");
        Student student = new Student(1,  "Alexander", "Alexandrov", group);

        doNothing().when(studentService).update(student);

        mockMvc.perform(patch("/students/{id}/edit", 1L)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Alexander")
                .param("lastName", "Alexandrov")
                .param("group.id", "1")
                .sessionAttr("student", student)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/students"))
                .andExpect(model().attribute("student", hasProperty("firstName",
                        is("Alexander"))))
                .andExpect(model().attribute("student", hasProperty("lastName",
                        is("Alexandrov"))));

        verify(studentService, times(1)).update(student);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void addNew_ShouldAddEmptyStudentToModelAndRenderStudentAddView() throws Exception {
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/new"))
                .andExpect(model().attribute("newStudent", hasProperty("firstName", nullValue())));
    }

    @Test
    void create_ShouldAddStudentToModelAndRedirectToStudentListViewAndCallTheCreateDaoMethodOnce() throws Exception {
        Group group = new Group(1, "Aqua");
        Student student = new Student(0,  "Viktor", "Viktorov", group);

        doNothing().when(studentService).create(student);

        mockMvc.perform(post("/students/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Viktor")
                .param("lastName", "Viktorov")
                .param("group.id", "1")
                .sessionAttr("student", student)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/students"))
                .andExpect(model().attribute("student", hasProperty("firstName",
                        is("Viktor"))))
                .andExpect(model().attribute("student", hasProperty("lastName",
                        is("Viktorov"))));

        verify(studentService, times(1)).create(student);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void delete_ShouldRedirectToStudentsListViewAndCallTheDeleteDaoMethodOnce() throws Exception {
        final int id = 1;

        doNothing().when(studentService).delete(id);

        mockMvc.perform(delete("/students/{id}", 1L))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/students"));

        verify(studentService, times(1)).delete(id);
        verifyNoMoreInteractions(studentService);
    }
}
