package com.ashna.focus_tracker.repository;

import com.ashna.focus_tracker.entity.Goal;
import com.ashna.focus_tracker.entity.GoalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal> findByStatus(GoalStatus status);
}