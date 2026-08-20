package org.example.studentperformanceweb.controller;

import org.example.studentperformanceweb.entity.User;
import org.example.studentperformanceweb.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // =====================================================
    // SHOW LOGIN PAGE
    // =====================================================

    @GetMapping("/")
    public String showLoginPage() {

        return "login";
    }


    // =====================================================
    // PROCESS LOGIN
    // =====================================================

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {


        // Remove extra spaces
        username = username.trim();


        // Find user from database
        Optional<User> userOptional =
                userRepository.findByUsername(username);


        // =================================================
        // USER FOUND
        // =================================================

        if (userOptional.isPresent()) {

            User user = userOptional.get();


            // =================================================
            // CHECK PASSWORD
            // =================================================

            if (user.getPassword().equals(password)) {


                // Save logged-in user in session
                session.setAttribute("loggedInUser", user);

                // Save username
                session.setAttribute("username", user.getUsername());

                // Save role
                session.setAttribute("role", user.getRole());


                // =================================================
                // ADMIN LOGIN
                // =================================================

                if ("ADMIN".equalsIgnoreCase(user.getRole())) {

                    return "redirect:/admin/dashboard";
                }


                // =================================================
                // STUDENT LOGIN
                // =================================================

                if ("STUDENT".equalsIgnoreCase(user.getRole())) {

                    // Save student ID in session
                    session.setAttribute(
                            "studentId",
                            user.getStudentId()
                    );

                    return "redirect:/student/dashboard";
                }


                // =================================================
                // INVALID ROLE
                // =================================================

                model.addAttribute(
                        "error",
                        "Invalid user role!"
                );

                return "login";
            }
        }


        // =================================================
        // INVALID LOGIN
        // =================================================

        model.addAttribute(
                "error",
                "Invalid username or password!"
        );

        return "login";
    }
}