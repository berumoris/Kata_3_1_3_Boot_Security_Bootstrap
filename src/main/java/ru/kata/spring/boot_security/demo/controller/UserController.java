package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImp userService;

    @GetMapping("/admin")
    public String getAllUsers(Model model, Authentication authentication) {
        model.addAttribute("admin", userService.getUserByEmail(authentication.getName()));
        model.addAttribute("users", userService.findAllUsers());
        return "admin";
    }

    @GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user, Model model, Authentication authentication) {
        model.addAttribute("admin", userService.getUserByEmail(authentication.getName()));
        return "new";
    }

    @PostMapping("/admin")
    public String addNewUser(@ModelAttribute User user, @RequestParam(required = false) String[] role) {
        userService.saveUser(user);
        if (role != null) {
            for (String r : role) {
                userService.setUserRole(user.getEmail(), r);
            }
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles().stream().map(x -> x.getNameRole()).collect(Collectors.toSet()));
        return "edit";
    }

    @PatchMapping("/admin")
    public String updateUser(@ModelAttribute User user, @RequestParam(required = false) String[] role, @RequestParam(required = false) String password) {

        userService.updateUser(user, password);
        if (role != null) {
            for (String r : role) {
                userService.setUserRole(user.getEmail(), r);
            }
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getUser(Model model, Authentication authentication) {
        model.addAttribute("users", userService.getUserByEmail(authentication.getName()));
        return "user";
    }
}