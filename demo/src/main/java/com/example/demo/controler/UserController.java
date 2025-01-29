package com.example.demo.controler;

import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        userService.createUser(username, password, "ROLE_USER");
        return "redirect:/login";
    }

    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }
}