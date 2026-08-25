package com.ashna.focus_tracker.repository;

import com.ashna.focus_tracker.entity.Task;
import com.ashna.focus_tracker.entity.Goal;
import com.ashna.focus_tracker.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDate(LocalDate date);

    List<Task> findByGoalAndDate(Goal goal, LocalDate date);

    List<Task> findByStatusAndDate(TaskStatus status, LocalDate date);
}