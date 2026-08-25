package com.ashna.focus_tracker.repository;

import com.ashna.focus_tracker.entity.GoalComment;
import com.ashna.focus_tracker.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GoalCommentRepository extends JpaRepository<GoalComment, Long> {

    List<GoalComment> findByGoalOrderByCreatedAtDesc(Goal goal);
}