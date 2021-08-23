package com.foxminded.university.controller;

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
import com.foxminded.university.service.LectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
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
public class LecturesControllerTest {
    private MockMvc mockMvc;

    @Mock
    private LectureService lectureService;

    @InjectMocks
    private LecturesController lecturesController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lecturesController).build();
    }

    @Test
    void showAll_ShouldAddLecturesToModelAndRenderPaginatedLecturesListViewAndCallTheShowAllDaoMethodOnce()
            throws Exception {
        Teacher teacher = new Teacher(1, "Ivan", "Ivanov");
        Group group = new Group(1, "Backstreet Boys");
        Lecture firstLecture =
                new Lecture(1, teacher, "Folk dances",
                        "Show how to dance", group);
        Lecture secondLecture =
                new Lecture(2, teacher, "Beekeeping lessons",
                        "Show how to breed bees for honey", group);

        int currentPage = 1;
        int pageSize = 12;

        List<Lecture> lectures = Arrays.asList(firstLecture, secondLecture);

        Page<Lecture> page = new PageImpl<>(lectures, PageRequest.of(0, 2), lectures.size());

        when(lectureService.findPaginated(isA(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/lectures"))
                .andExpect(status().isOk())
                .andExpect(view().name("lectures/lectures"))
                .andExpect(model().attributeExists("lecturePage"))
                .andExpect(model().attribute("lecturePage",
                        hasProperty("totalElements", equalTo(2L))));

        verify(lectureService, times(1))
                .findPaginated(PageRequest.of(currentPage - 1, pageSize));
        verifyNoMoreInteractions(lectureService);
    }

    @Test
    void showEach_ShouldAddLectureToModelAndRenderLectureEditViewAndCallTheGetByIdDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, "Boris", "Borisov");
        Group group = new Group(1, "N Sync");
        Lecture lecture =
                new Lecture(1, teacher, "Admiring nature",
                        "Show how to admire nature", group);

        when(lectureService.getById(1)).thenReturn(lecture);

        mockMvc.perform(get("/lectures/{id}/edit", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("lectures/edit"))
                .andExpect(model().attribute("lectureId",
                        hasProperty("teacher", is(teacher))))
                .andExpect(model().attribute("lectureId",
                        hasProperty("lectureName", is("Admiring nature"))))
                .andExpect(model().attribute("lectureId",
                        hasProperty("description", is("Show how to admire nature"))))
                .andExpect(model().attribute("lectureId",
                        hasProperty("group", is(group))));

        verify(lectureService, times(1)).getById(1);
        verifyNoMoreInteractions(lectureService);
    }

    @Test
    void update_ShouldAddLectureToModelAndRedirectToLecturesListViewAndCallTheUpdateDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, "Null", "Nullov");
        Group group = new Group(1, "Aqua");
        Lecture lecture =
                new Lecture(1, teacher, "Theory and practice of cyber warfare",
                        "Show how to fight", group);

        doNothing().when(lectureService).update(lecture);

        mockMvc.perform(patch("/lectures/{id}/edit", 1L)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("lectureName", "Theory and practice of cyber warfare")
                .param("description", "Show how to fight")
                .param("teacher.id", "1")
                .param("group.id", "1")
                .sessionAttr("lecture", lecture)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/lectures"))
                .andExpect(model().attribute("lecture", hasProperty("lectureName",
                        is("Theory and practice of cyber warfare"))))
                .andExpect(model().attribute("lecture", hasProperty("description",
                        is("Show how to fight"))));

        verify(lectureService, times(1)).update(lecture);
        verifyNoMoreInteractions(lectureService);
    }

    @Test
    void addNew_ShouldAddEmptyLectureToModelAndRenderLectureAddView() throws Exception {
        mockMvc.perform(get("/lectures/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("lectures/new"))
                .andExpect(model().attribute("newLecture", hasProperty("lectureName", nullValue())));
    }

    @Test
    void create_ShouldAddLectureToModelAndRedirectToLectureListViewAndCallTheCreateDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, null, null);
        Group group = new Group(1, null);
        Lecture lecture =
                new Lecture(0, teacher, "Practical magic", "Foxminded mentoring", group);

        doNothing().when(lectureService).create(lecture);

        mockMvc.perform(post("/lectures/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("lectureName", "Practical magic")
                .param("description", "Foxminded mentoring")
                .param("teacher.id", "1")
                .param("group.id", "1")
                .sessionAttr("lecture", lecture)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/lectures"))
                .andExpect(model().attribute("lecture", hasProperty("lectureName",
                        is("Practical magic"))))
                .andExpect(model().attribute("lecture", hasProperty("description",
                        is("Foxminded mentoring"))));

        verify(lectureService, times(1)).create(lecture);
        verifyNoMoreInteractions(lectureService);
    }

    @Test
    void delete_ShouldRedirectToLecturesListViewAndCallTheDeleteDaoMethodOnce() throws Exception {
        final int id = 1;

        doNothing().when(lectureService).delete(id);

        mockMvc.perform(delete("/lectures/{id}", 1L))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/lectures"));

        verify(lectureService, times(1)).delete(id);
        verifyNoMoreInteractions(lectureService);
    }
}
