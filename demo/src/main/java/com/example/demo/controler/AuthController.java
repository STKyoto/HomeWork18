package com.example.demo.controler;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        if (isValidUser(username, password)) {
            return "redirect:/welcome";
        } else {
            model.addAttribute("error", "Incorrect data");
            return "login";
        }
    }

    private boolean isValidUser(String username, String password) {
        Optional<User> newUser = userRepository.findByUserName(username);
        if (newUser.isPresent()){
            User user = newUser.get();
            return username.equals(user.getUserName()) && passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
