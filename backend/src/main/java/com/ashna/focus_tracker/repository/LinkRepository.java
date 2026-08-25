package com.ashna.focus_tracker.repository;

import com.ashna.focus_tracker.entity.Link;
import com.ashna.focus_tracker.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findByGoal(Goal goal);
}