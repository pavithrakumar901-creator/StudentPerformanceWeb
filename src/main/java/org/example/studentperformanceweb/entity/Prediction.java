package org.example.studentperformanceweb.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "predictions")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prediction_id")
    private Integer predictionId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "predicted_performance", nullable = false)
    private String predictedPerformance;

    @Column(name = "prediction_date", nullable = false)
    private LocalDateTime predictionDate;

    public Prediction() {
    }

    public Integer getPredictionId() {
        return predictionId;
    }

    public void setPredictionId(Integer predictionId) {
        this.predictionId = predictionId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getPredictedPerformance() {
        return predictedPerformance;
    }

    public void setPredictedPerformance(String predictedPerformance) {
        this.predictedPerformance = predictedPerformance;
    }

    public LocalDateTime getPredictionDate() {
        return predictionDate;
    }

    public void setPredictionDate(LocalDateTime predictionDate) {
        this.predictionDate = predictionDate;
    }
}