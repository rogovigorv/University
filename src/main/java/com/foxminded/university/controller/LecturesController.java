package com.foxminded.university.controller;

import com.foxminded.university.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lectures")
public class LecturesController {
    private final LectureService lectureService;

    @Autowired
    public LecturesController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping
    public String showAllLectures(Model model) {
        model.addAttribute("lectures", lectureService.showAll());
        return "lectures/show";
    }
}
