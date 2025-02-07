package com.example.demo.controler;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Authentication authentication,
                        BindingResult bindingResult,
                        Model model) {
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/main";
        } catch (BadCredentialsException e) {
            bindingResult.rejectValue("password", "BadCredentials", "Invalid username or password.");
        } catch (Exception e) {
            bindingResult.rejectValue("password", "InternalError", "An error occurred.");
        }
        return "login";
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
