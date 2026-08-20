package org.example.studentperformanceweb.controller;

import org.example.studentperformanceweb.entity.Student;
import org.example.studentperformanceweb.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final StudentRepository studentRepository;

    public AdminController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Admin Dashboard
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    // Manage Students
    @GetMapping("/admin/students")
    public String viewStudents(Model model) {

        List<Student> students = studentRepository.findAll();

        model.addAttribute("students", students);

        return "students";
    }
}