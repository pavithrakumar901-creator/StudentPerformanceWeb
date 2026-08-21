package org.example.studentperformanceweb.controller;

import org.example.studentperformanceweb.entity.Student;
import org.example.studentperformanceweb.entity.User;
import org.example.studentperformanceweb.repository.StudentRepository;
import org.example.studentperformanceweb.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public UserController(
            UserRepository userRepository,
            StudentRepository studentRepository) {

        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    // =========================================
    // USER LIST
    // =========================================

    @GetMapping("/admin/users")
    public String usersPage(Model model) {

        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);

        return "users";
    }

    // =========================================
    // ADD USER PAGE
    // =========================================

    @GetMapping("/admin/users/add")
    public String addUserPage(Model model) {

        model.addAttribute("user", new User());

        return "add-user";
    }

    // =========================================
    // SAVE USER + STUDENT
    // =========================================

    @PostMapping("/admin/users/add")
    public String addUser(

            @RequestParam("username")
            String username,

            @RequestParam("password")
            String password,

            @RequestParam("role")
            String role,

            @RequestParam(value = "name", required = false)
            String name,

            @RequestParam(value = "department", required = false)
            String department,

            @RequestParam(value = "attendance", required = false)
            Double attendance,

            @RequestParam(value = "internalMark", required = false)
            Double internalMark,

            @RequestParam(value = "assignmentMark", required = false)
            Double assignmentMark,

            @RequestParam(value = "previousMark", required = false)
            Double previousMark,

            @RequestParam(value = "studyHours", required = false)
            Double studyHours,

            Model model) {

        username = username.trim();
        role = role.trim().toUpperCase();

        // =====================================
        // CHECK USERNAME
        // =====================================

        Optional<User> existingUser =
                userRepository.findByUsername(username);

        if (existingUser.isPresent()) {

            model.addAttribute(
                    "error",
                    "Username already exists!"
            );

            return "add-user";
        }

        // =====================================
        // CHECK ROLE
        // =====================================

        if (!role.equals("STUDENT") &&
                !role.equals("ADMIN")) {

            model.addAttribute(
                    "error",
                    "Please select a valid role."
            );

            return "add-user";
        }

        // =====================================
        // CREATE USER
        // =====================================

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        // =====================================
        // STUDENT USER
        // =====================================

        if ("STUDENT".equals(role)) {

            // Check required student details

            if (name == null || name.trim().isEmpty()) {

                model.addAttribute(
                        "error",
                        "Please enter Student Name."
                );

                return "add-user";
            }

            if (department == null ||
                    department.trim().isEmpty()) {

                model.addAttribute(
                        "error",
                        "Please enter Department."
                );

                return "add-user";
            }

            if (attendance == null ||
                    internalMark == null ||
                    assignmentMark == null ||
                    previousMark == null ||
                    studyHours == null) {

                model.addAttribute(
                        "error",
                        "Please fill all student details."
                );

                return "add-user";
            }

            // =================================
            // CREATE STUDENT
            // =================================

            Student student = new Student();

            student.setName(name.trim());
            student.setDepartment(department.trim());
            student.setAttendance(attendance);
            student.setInternalMark(internalMark);
            student.setAssignmentMark(assignmentMark);
            student.setPreviousMark(previousMark);
            student.setStudyHours(studyHours);

            // Save student first.
            // studentId is generated automatically.

            Student savedStudent =
                    studentRepository.save(student);

            System.out.println(
                    "Student saved successfully. Student ID = "
                            + savedStudent.getStudentId()
            );

            // =================================
            // LINK USER TO GENERATED STUDENT ID
            // =================================

            user.setStudentId(
                    savedStudent.getStudentId()
            );
        }

        // =====================================
        // ADMIN USER
        // =====================================

        else {

            user.setStudentId(null);
        }

        // =====================================
        // SAVE USER
        // =====================================

        try {

            userRepository.save(user);

            System.out.println(
                    "User saved successfully: "
                            + username
            );

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Unable to create user. Please try again."
            );

            return "add-user";
        }

        // =====================================
        // SUCCESS
        // =====================================

        return "redirect:/admin/users";
    }

    // =========================================
    // DELETE USER
    // =========================================

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(
            @PathVariable Integer id) {

        userRepository.deleteById(id);


        return "redirect:/admin/users";
    }
}
