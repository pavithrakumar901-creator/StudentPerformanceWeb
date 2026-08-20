package org.example.studentperformanceweb.controller;

import org.example.studentperformanceweb.entity.User;
import org.example.studentperformanceweb.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ==========================================
    // SHOW LOGIN PAGE
    // ==========================================

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // ==========================================
    // PROCESS LOGIN
    // ==========================================

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        User user = userRepository.findByUsername(username);

        // Username not found
        if (user == null) {
            model.addAttribute(
                    "error",
                    "Invalid username or password"
            );
            return "login";
        }

        // Password check
        if (user.getPassword() == null
                || !user.getPassword().equals(password)) {

            model.addAttribute(
                    "error",
                    "Invalid username or password"
            );
            return "login";
        }

        // ==========================================
        // ADMIN LOGIN
        // ==========================================

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        // ==========================================
        // STUDENT LOGIN
        // ==========================================

        if ("STUDENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/student/dashboard";
        }

        // ==========================================
        // INVALID ROLE
        // ==========================================

        model.addAttribute(
                "error",
                "Invalid user role"
        );

        return "login";
    }
}
