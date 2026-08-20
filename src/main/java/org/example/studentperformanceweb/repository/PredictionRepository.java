package org.example.studentperformanceweb.repository;

import org.example.studentperformanceweb.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRepository
        extends JpaRepository<Prediction, Integer> {

    List<Prediction> findAllByOrderByPredictionDateDesc();
}