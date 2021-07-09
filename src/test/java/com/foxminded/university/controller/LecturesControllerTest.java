package com.foxminded.university.controller;

import com.foxminded.university.config.SpringConfigTest;
import com.foxminded.university.dao.LectureDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
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
public class LecturesControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private LectureDao lectureDaoMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        Mockito.reset(lectureDaoMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void showAll_ShouldAddLecturesToModelAndRenderPaginatedLecturesListViewAndCallTheShowAllDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, "Ivan", "Ivanov");
        Group group = new Group(1, "Backstreet Boys");
        Lecture firstLecture =
                new Lecture(1, teacher, "Folk dances",
                        "Show how to dance", group);
        Lecture secondLecture =
                new Lecture(2, teacher, "Beekeeping lessons",
                        "Show how to breed bees for honey", group);

        List<Lecture> lectures = new ArrayList<>();
        lectures.add(firstLecture);
        lectures.add(secondLecture);

        when(lectureDaoMock.showAll()).thenReturn(lectures);

        mockMvc.perform(get("/lectures"))
                .andExpect(status().isOk())
                .andExpect(view().name("lectures/lectures"))
                .andExpect(model().attributeExists("lecturePage"))
                .andExpect(model().attribute("lecturePage",
                        hasProperty("totalElements", equalTo(2L))));

        verify(lectureDaoMock, times(1)).showAll();
        verifyNoMoreInteractions(lectureDaoMock);
    }

    @Test
    void showEach_ShouldAddLectureToModelAndRenderLectureEditViewAndCallTheGetByIdDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, "Boris", "Borisov");
        Group group = new Group(1, "N Sync");
        Lecture lecture =
                new Lecture(1, teacher, "Admiring nature",
                        "Show how to admire nature", group);

        when(lectureDaoMock.getById(1)).thenReturn(lecture);

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

        verify(lectureDaoMock, times(1)).getById(1);
        verifyNoMoreInteractions(lectureDaoMock);
    }

    @Test
    void update_ShouldAddLectureToModelAndRedirectToLecturesListViewAndCallTheUpdateDaoMethodOnce() throws Exception {
        Teacher teacher = new Teacher(1, "Null", "Nullov");
        Group group = new Group(1, "Aqua");
        Lecture lecture =
                new Lecture(1, teacher, "Theory and practice of cyber warfare",
                        "Show how to fight", group);

        doNothing().when(lectureDaoMock).update(lecture);

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

        verify(lectureDaoMock, times(1)).update(lecture);
        verifyNoMoreInteractions(lectureDaoMock);
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

        doNothing().when(lectureDaoMock).create(lecture);

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

        verify(lectureDaoMock, times(1)).create(lecture);
        verifyNoMoreInteractions(lectureDaoMock);
    }

    @Test
    void delete_ShouldRedirectToLecturesListViewAndCallTheDeleteDaoMethodOnce() throws Exception {
        final int id = 1;

        doNothing().when(lectureDaoMock).delete(id);

        mockMvc.perform(delete("/lectures/{id}", 1L))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/lectures"));

        verify(lectureDaoMock, times(1)).delete(id);
        verifyNoMoreInteractions(lectureDaoMock);
    }
}
