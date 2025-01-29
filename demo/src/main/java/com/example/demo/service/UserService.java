package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createUser(String username, String rawPassword, String roleName) {
        Optional<User> existingUser = userRepository.findByUserName(username);
        if (existingUser.isPresent()) {
            return "User already exist";
        }

        Role role = roleRepository.findByName(roleName).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(roleName);
            return roleRepository.save(newRole);
        });

        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        newUser.getRoles().add(role);
        userRepository.save(newUser);

        return "User successfuly created";
    }
}
