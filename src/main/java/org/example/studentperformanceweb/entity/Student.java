package org.example.studentperformanceweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "attendance")
    private Double attendance;

    @Column(name = "internal_mark")
    private Double internalMark;

    @Column(name = "assignment_mark")
    private Double assignmentMark;

    @Column(name = "previous_mark")
    private Double previousMark;

    @Column(name = "study_hours")
    private Double studyHours;

    public Student() {
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getAttendance() {
        return attendance;
    }

    public void setAttendance(Double attendance) {
        this.attendance = attendance;
    }

    public Double getInternalMark() {
        return internalMark;
    }

    public void setInternalMark(Double internalMark) {
        this.internalMark = internalMark;
    }

    public Double getAssignmentMark() {
        return assignmentMark;
    }

    public void setAssignmentMark(Double assignmentMark) {
        this.assignmentMark = assignmentMark;
    }

    public Double getPreviousMark() {
        return previousMark;
    }

    public void setPreviousMark(Double previousMark) {
        this.previousMark = previousMark;
    }

    public Double getStudyHours() {
        return studyHours;
    }

    public void setStudyHours(Double studyHours) {
        this.studyHours = studyHours;
    }
}