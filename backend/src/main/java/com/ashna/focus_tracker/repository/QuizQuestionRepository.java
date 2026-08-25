package com.ashna.focus_tracker.repository;

import com.ashna.focus_tracker.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
}