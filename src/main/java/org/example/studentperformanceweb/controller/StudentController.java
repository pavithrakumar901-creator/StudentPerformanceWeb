package org.example.studentperformanceweb.controller;

import org.example.studentperformanceweb.entity.Student;
import org.example.studentperformanceweb.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
            Model model,
            HttpSession session) {

        // Get logged-in student's ID from session
        Object studentIdObject = session.getAttribute("studentId");

        // If student is not logged in
        if (studentIdObject == null) {
            return "redirect:/";
        }

        Integer studentId = (Integer) studentIdObject;

        // Find student from database
        Optional<Student> studentOptional =
                studentRepository.findById(studentId);

        // Student found
        if (studentOptional.isPresent()) {

            Student student = studentOptional.get();

            // Send student data to HTML
            model.addAttribute("student", student);

            // Send username to HTML
            model.addAttribute(
                    "username",
                    session.getAttribute("username")
            );

            return "student-dashboard";
        }

        // Student ID not found in database
        model.addAttribute(
                "error",
                "Student details not found!"
        );

        return "student-dashboard";
    }
}