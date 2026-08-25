package com.ashna.focus_tracker.repository;

import com.ashna.focus_tracker.entity.UserAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;
import java.util.List;

public interface UserAvailabilityRepository extends JpaRepository<UserAvailability, Long> {

    List<UserAvailability> findByDayOfWeekOrDayOfWeekIsNull(DayOfWeek dayOfWeek);
}