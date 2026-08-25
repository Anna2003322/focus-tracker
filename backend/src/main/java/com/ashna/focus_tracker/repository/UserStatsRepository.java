package com.ashna.focus_tracker.repository;

import com.ashna.focus_tracker.entity.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatsRepository extends JpaRepository<UserStats, Long> {
}