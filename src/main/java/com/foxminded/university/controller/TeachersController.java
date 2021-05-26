package com.foxminded.university.controller;

import com.foxminded.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teachers")
public class TeachersController {
    private final TeacherService teacherService;

    @Autowired
    public TeachersController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String showAllTeachers(Model model) {
        model.addAttribute("teachers", teacherService.showAll());
        return "teachers/show";
    }
}
