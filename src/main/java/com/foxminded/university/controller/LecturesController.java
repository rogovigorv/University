package com.foxminded.university.controller;

import com.foxminded.university.models.Lecture;
import com.foxminded.university.service.LectureService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/lectures")
public class LecturesController {
    private final LectureService lectureService;

    @Autowired
    public LecturesController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping()
    public String showAll(Model model, @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);

        Page<Lecture> lecturePage = lectureService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("lecturePage", lecturePage);

        int totalPages = lecturePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                                 .boxed()
                                                 .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "lectures/lectures";
    }

    @GetMapping("/{id}/edit")
    public String showEach(@PathVariable("id") long id, Model model) {
        model.addAttribute("lectureId", lectureService.getById(id));
        return "lectures/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("lecture") Lecture lecture) {
        lectureService.update(lecture);
        return "redirect:/lectures";
    }

    @GetMapping("/new")
    public String addNew(Model model) {
        model.addAttribute("newLecture", new Lecture());
        return "lectures/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("lecture") Lecture lecture) {
        lectureService.create(lecture);
        return "redirect:/lectures";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        lectureService.delete(id);
        return "redirect:/lectures";
    }
}
