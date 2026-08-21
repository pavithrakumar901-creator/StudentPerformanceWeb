package org.example.studentperformanceweb.controller;

import org.example.studentperformanceweb.entity.Student;
import org.example.studentperformanceweb.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ==========================================
    // STUDENT DASHBOARD
    // ==========================================

    @GetMapping("/student/dashboard")
    public String studentDashboard(
            @RequestParam(value = "studentId", required = false)
            Integer studentId,

            Model model,
            HttpSession session) {

        // ==========================================
        // GET STUDENT ID
        // ==========================================

        // First check URL parameter
        if (studentId != null) {

            // Save student ID in session
            session.setAttribute("studentId", studentId);

        } else {

            // If URL parameter is not available,
            // get student ID from session

            Object studentIdObject =
                    session.getAttribute("studentId");

            if (studentIdObject == null) {

                return "redirect:/login?error=true";
            }

            studentId = (Integer) studentIdObject;
        }

        // ==========================================
        // FIND STUDENT
        // ==========================================

        Optional<Student> studentOptional =
                studentRepository.findById(studentId);

        // ==========================================
        // STUDENT FOUND
        // ==========================================

        if (studentOptional.isPresent()) {

            Student student = studentOptional.get();

            // Send student data to HTML
            model.addAttribute(
                    "student",
                    student
            );

            // Send username to HTML
            model.addAttribute(
                    "username",
                    session.getAttribute("username")
            );

            return "student-dashboard";
        }

        // ==========================================
        // STUDENT NOT FOUND
        // ==========================================

        model.addAttribute(
                "error",
                "Student details not found!"
        );

        return "student-dashboard";
    }
}