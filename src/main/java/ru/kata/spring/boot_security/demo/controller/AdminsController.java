package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;


@Controller
@RequestMapping({"/admin"})
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("user", this.userService.findByEmail(principal.getName()));
        model.addAttribute("users", this.userService.getAllUsers());
        model.addAttribute("roles", this.roleService.getListRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PutMapping({"/edit/{id}"})
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id, Model model) {
        this.userService.updateUser(id, user);
           return "redirect:/admin";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        this.userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping({"/{id}"})
    public String delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
        return "redirect:/admin";
    }
}
