package org.example.studentperformanceweb.controller;

import org.example.studentperformanceweb.entity.Prediction;
import org.example.studentperformanceweb.entity.Student;
import org.example.studentperformanceweb.repository.PredictionRepository;
import org.example.studentperformanceweb.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PredictionController {

    private final StudentRepository studentRepository;
    private final PredictionRepository predictionRepository;

    public PredictionController(
            StudentRepository studentRepository,
            PredictionRepository predictionRepository) {

        this.studentRepository = studentRepository;
        this.predictionRepository = predictionRepository;
    }

    // ================================
    // PREDICTION PAGE
    // ================================

    @GetMapping("/admin/prediction")
    public String predictionPage(Model model) {

        List<Student> students = studentRepository.findAll();

        model.addAttribute("students", students);

        return "prediction";
    }

    // ================================
    // SAVE PREDICTION
    // ================================

    @PostMapping("/admin/save-prediction")
    @ResponseBody
    public String savePrediction(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("performance") String performance) {

        try {

            Prediction prediction = new Prediction();

            prediction.setStudentId(studentId);
            prediction.setPredictedPerformance(performance);
            prediction.setPredictionDate(LocalDateTime.now());

            predictionRepository.save(prediction);

            return "success";

        } catch (Exception e) {

            e.printStackTrace();

            return "error";
        }
    }

    // ================================
    // PREDICTION HISTORY
    // ================================

    @GetMapping("/admin/prediction-history")
    public String predictionHistory(Model model) {

        List<Prediction> predictions =
                predictionRepository.findAllByOrderByPredictionDateDesc();

        model.addAttribute("predictions", predictions);

        return "prediction-history";
    }
}