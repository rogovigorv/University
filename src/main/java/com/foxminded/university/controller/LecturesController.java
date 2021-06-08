package com.foxminded.university.controller;

import com.foxminded.university.models.Lecture;
import com.foxminded.university.service.LectureService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/lectures")
public class LecturesController {
    private final LectureService lectureService;

    @Autowired
    public LecturesController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping()
    public String showAllLectures(Model model, @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(17);

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
}
