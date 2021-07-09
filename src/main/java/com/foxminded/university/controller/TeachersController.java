package com.foxminded.university.controller;

import com.foxminded.university.models.Teacher;
import com.foxminded.university.service.TeacherService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teachers")
public class TeachersController {
    private final TeacherService teacherService;

    @Autowired
    public TeachersController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping()
    public String showAllLectures(Model model, @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(17);

        Page<Teacher> teacherPage = teacherService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("teacherPage", teacherPage);

        int totalPages = teacherPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                                 .boxed()
                                                 .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "teachers/teachers";
    }

    @GetMapping("/{id}/edit")
    public String showEach(@PathVariable("id") int id, Model model) {
        model.addAttribute("teacherId", teacherService.getById(id));
        return "teachers/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("teacher") Teacher teacher) {
        teacherService.update(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/new")
    public String addNew(Model model) {
        model.addAttribute("newTeacher", new Teacher());
        return "teachers/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("teacher") Teacher teacher) {
        teacherService.create(teacher);
        return "redirect:/teachers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        teacherService.delete(id);
        return "redirect:/teachers";
    }
}
