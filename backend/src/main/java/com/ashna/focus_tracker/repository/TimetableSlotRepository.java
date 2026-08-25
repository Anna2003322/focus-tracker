package com.ashna.focus_tracker.repository;

import com.ashna.focus_tracker.entity.TimetableSlot;
import com.ashna.focus_tracker.entity.SlotSource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;
import java.util.List;

public interface TimetableSlotRepository extends JpaRepository<TimetableSlot, Long> {

    List<TimetableSlot> findByDayOfWeek(DayOfWeek dayOfWeek);

    List<TimetableSlot> findBySource(SlotSource source);
}