package org.example.studentperformanceweb.controller;

import org.example.studentperformanceweb.entity.User;
import org.example.studentperformanceweb.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Open website / or /login
    @GetMapping({"/", "/login"})
    public String loginPage() {
        return "login";
    }

    // Process login
    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        Optional<User> userOptional =
                userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            model.addAttribute("error",
                    "Invalid username or password");
            return "login";
        }

        User user = userOptional.get();

        if (user.getPassword() == null ||
                !user.getPassword().equals(password)) {

            model.addAttribute("error",
                    "Invalid username or password");
            return "login";
        }

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/admin/dashboard";
        }

        if ("STUDENT".equalsIgnoreCase(user.getRole())) {
            return "redirect:/student/dashboard";
        }

        model.addAttribute("error", "Invalid user role");
        return "login";
    }
}
