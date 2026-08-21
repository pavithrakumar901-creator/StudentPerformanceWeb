package org.example.studentperformanceweb.controller;

import org.example.studentperformanceweb.entity.User;
import org.example.studentperformanceweb.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =========================================
    // SHOW LOGIN PAGE
    // =========================================

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false)
            String error,

            @RequestParam(value = "logout", required = false)
            String logout,

            Model model) {

        if (error != null) {
            model.addAttribute(
                    "error",
                    "Invalid username or password!"
            );
        }

        if (logout != null) {
            model.addAttribute(
                    "success",
                    "You have been logged out successfully."
            );
        }

        return "login";
    }


    // =========================================
    // PROCESS LOGIN
    // =========================================

    @PostMapping("/login")
    public String login(
            @RequestParam("username")
            String username,

            @RequestParam("password")
            String password,

            Model model) {

        username = username.trim();

        Optional<User> userOptional =
                userRepository.findByUsername(username);

        // -----------------------------------------
        // USER NOT FOUND
        // -----------------------------------------

        if (userOptional.isEmpty()) {

            model.addAttribute(
                    "error",
                    "Invalid username or password!"
            );

            return "login";
        }

        User user = userOptional.get();


        // -----------------------------------------
        // CHECK PASSWORD
        // -----------------------------------------

        if (user.getPassword() == null ||
                !user.getPassword().equals(password)) {

            model.addAttribute(
                    "error",
                    "Invalid username or password!"
            );

            return "login";
        }


        // -----------------------------------------
        // CHECK ROLE
        // -----------------------------------------

        String role = user.getRole();

        if (role == null || role.trim().isEmpty()) {

            model.addAttribute(
                    "error",
                    "User role is not configured!"
            );

            return "login";
        }


        role = role.trim().toUpperCase();


        // -----------------------------------------
        // ADMIN LOGIN
        // -----------------------------------------

        if ("ADMIN".equals(role)) {

            return "redirect:/admin/dashboard";
        }


        // -----------------------------------------
        // STUDENT LOGIN
        // -----------------------------------------

        if ("STUDENT".equals(role)) {

            if (user.getStudentId() == null) {

                model.addAttribute(
                        "error",
                        "Student account is not linked to a student!"
                );

                return "login";
            }

            return "redirect:/student/dashboard?studentId="
                    + user.getStudentId();
        }


        // -----------------------------------------
        // INVALID ROLE
        // -----------------------------------------

        model.addAttribute(
                "error",
                "Invalid user role!"
        );

        return "login";
    }
}
