package com.foxminded.university.controller;

import com.foxminded.university.models.Group;
import com.foxminded.university.service.GroupService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/groups")
public class GroupsController {
    private final GroupService groupService;

    @Autowired
    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public String showAll(Model model, @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);

        Page<Group> groupPage = groupService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("groupPage", groupPage);

        int totalPages = groupPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                                                 .boxed()
                                                 .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "groups/groups";
    }

    @GetMapping("/{id}/edit")
    public String showEach(@PathVariable("id") int id, Model model) {
        model.addAttribute("groupId", groupService.getById(id));
        return "groups/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("group") Group group) {
        groupService.update(group);
        return "redirect:/groups";
    }

    @GetMapping("/new")
    public String addNew(Model model) {
        model.addAttribute("newGroup", new Group());
        return "groups/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("group") Group group) {
        groupService.create(group);
        return "redirect:/groups";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        groupService.delete(id);
        return "redirect:/groups";
    }
}
