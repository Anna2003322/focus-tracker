package com.ashna.focus_tracker.scheduling;

import com.ashna.focus_tracker.dto.response.GenerateTimetableResponse;
import com.ashna.focus_tracker.dto.response.TimetableSlotResponse;
import com.ashna.focus_tracker.entity.*;
import com.ashna.focus_tracker.repository.GoalRepository;
import com.ashna.focus_tracker.repository.TimetableSlotRepository;
import com.ashna.focus_tracker.repository.UserAvailabilityRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimetableGeneratorService {

    private final GoalRepository goalRepository;
    private final UserAvailabilityRepository availabilityRepository;
    private final TimetableSlotRepository slotRepository;

    public TimetableGeneratorService(GoalRepository goalRepository,
                                     UserAvailabilityRepository availabilityRepository,
                                     TimetableSlotRepository slotRepository) {
        this.goalRepository = goalRepository;
        this.availabilityRepository = availabilityRepository;
        this.slotRepository = slotRepository;
    }

    public GenerateTimetableResponse generate(LocalDate startDate, int days) {
        // Which distinct days-of-week does this range touch? (e.g. 5 days from a Monday = Mon-Fri)
        Set<DayOfWeek> daysInRange = new LinkedHashSet<>();
        for (int i = 0; i < days; i++) {
            daysInRange.add(startDate.plusDays(i).getDayOfWeek());
        }

        List<Goal> activeGoals = goalRepository.findByStatus(GoalStatus.ACTIVE);
        List<UserAvailability> availabilityRows = availabilityRepository.findAll();

        // Regeneration rule: clear only AUTO_GENERATED slots, never touch EXCEL_IMPORT ones
        List<TimetableSlot> autoSlots = slotRepository.findBySource(SlotSource.AUTO_GENERATED);
        List<TimetableSlot> toDelete = autoSlots.stream()
                .filter(s -> daysInRange.contains(s.getDayOfWeek()))
                .collect(Collectors.toList());
        slotRepository.deleteAll(toDelete);

        List<Goal> offTimeGoals = filterByType(activeGoals, GoalType.OFF_TIME);
        List<Goal> learningGoals = filterByType(activeGoals, GoalType.LEARNING);
        List<Goal> otherGoals = activeGoals.stream()
                .filter(g -> g.getGoalType() != GoalType.OFF_TIME && g.getGoalType() != GoalType.LEARNING)
                .collect(Collectors.toList());

        List<TimetableSlot> createdSlots = new ArrayList<>();
        StringBuilder warning = new StringBuilder();

        for (DayOfWeek day : daysInRange) {
            // Step 2/3: build this day's free windows from availability minus excluded breaks
            List<TimeWindow> freeWindows = buildFreeWindows(day, availabilityRows);

            // Step 1: reserve OFF_TIME first, before anything else touches the calendar
            for (Goal goal : offTimeGoals) {
                placeGoalForDay(goal, day, freeWindows, createdSlots, true);
            }

            // Step 6: LEARNING goals try their preferred window first
            for (Goal goal : learningGoals) {
                placeGoalForDay(goal, day, freeWindows, createdSlots, true);
            }

            // Step 7: everything else, general greedy placement, no preference
            for (Goal goal : otherGoals) {
                boolean placed = placeGoalForDay(goal, day, freeWindows, createdSlots, false);
                if (!placed) {
                    warning.append(goal.getTitle()).append(" couldn't fit on ").append(day).append(". ");
                }
            }
        }

        slotRepository.saveAll(createdSlots);

        GenerateTimetableResponse response = new GenerateTimetableResponse();
        response.setSlots(createdSlots.stream().map(this::toResponse).collect(Collectors.toList()));
        response.setWarning(warning.length() > 0 ? warning.toString().trim() : null);
        return response;
    }

    private List<Goal> filterByType(List<Goal> goals, GoalType type) {
        return goals.stream().filter(g -> g.getGoalType() == type).collect(Collectors.toList());
    }

    // Builds today's usable gaps: start from working windows, subtract excluded breaks
    private List<TimeWindow> buildFreeWindows(DayOfWeek day, List<UserAvailability> rows) {
        List<UserAvailability> applicable = rows.stream()
                .filter(r -> r.getDayOfWeek() == null || r.getDayOfWeek() == day)
                .collect(Collectors.toList());

        List<TimeWindow> working = applicable.stream()
                .filter(r -> !r.isExcluded())
                .map(r -> new TimeWindow(r.getStartTime(), r.getEndTime()))
                .collect(Collectors.toList());

        List<TimeWindow> excluded = applicable.stream()
                .filter(UserAvailability::isExcluded)
                .map(r -> new TimeWindow(r.getStartTime(), r.getEndTime()))
                .collect(Collectors.toList());

        List<TimeWindow> result = new ArrayList<>();
        for (TimeWindow w : working) {
            result.addAll(subtractExcluded(w, excluded));
        }
        return result;
    }

    // Splits one working window around any excluded windows that overlap it
    private List<TimeWindow> subtractExcluded(TimeWindow working, List<TimeWindow> excluded) {
        List<TimeWindow> pieces = new ArrayList<>();
        pieces.add(working);

        for (TimeWindow ex : excluded) {
            List<TimeWindow> next = new ArrayList<>();
            for (TimeWindow piece : pieces) {
                if (ex.getEnd().compareTo(piece.getStart()) <= 0 || ex.getStart().compareTo(piece.getEnd()) >= 0) {
                    next.add(piece); // no overlap, keep as-is
                } else {
                    if (ex.getStart().isAfter(piece.getStart())) {
                        next.add(new TimeWindow(piece.getStart(), ex.getStart()));
                    }
                    if (ex.getEnd().isBefore(piece.getEnd())) {
                        next.add(new TimeWindow(ex.getEnd(), piece.getEnd()));
                    }
                }
            }
            pieces = next;
        }
        return pieces;
    }

    // Places one goal's daily chunk into the earliest gap that fits, preferring its window if set.
    // Mutates freeWindows to shrink the chosen gap after placing. Returns whether it succeeded.
    private boolean placeGoalForDay(Goal goal, DayOfWeek day, List<TimeWindow> freeWindows,
                                    List<TimetableSlot> createdSlots, boolean respectPreference) {
        int minutesNeeded = goal.getCommitmentMinutes();

        // Step 6: try the preferred window first, if one is set
        if (respectPreference && goal.getPreferredStartTime() != null && goal.getPreferredEndTime() != null) {
            for (int i = 0; i < freeWindows.size(); i++) {
                TimeWindow w = freeWindows.get(i);
                LocalTime effectiveStart = w.getStart().isAfter(goal.getPreferredStartTime()) ? w.getStart() : goal.getPreferredStartTime();
                LocalTime effectiveEnd = w.getEnd().isBefore(goal.getPreferredEndTime()) ? w.getEnd() : goal.getPreferredEndTime();
                if (effectiveStart.isBefore(effectiveEnd)) {
                    long available = java.time.temporal.ChronoUnit.MINUTES.between(effectiveStart, effectiveEnd);
                    if (available >= minutesNeeded) {
                        LocalTime slotEnd = effectiveStart.plusMinutes(minutesNeeded);
                        createdSlots.add(buildSlot(day, effectiveStart, slotEnd, goal));
                        shrinkWindow(freeWindows, i, w, effectiveStart, slotEnd);
                        return true;
                    }
                }
            }
        }

        // Step 7: general greedy placement — earliest gap that fits
        for (int i = 0; i < freeWindows.size(); i++) {
            TimeWindow w = freeWindows.get(i);
            if (w.durationMinutes() >= minutesNeeded) {
                LocalTime slotEnd = w.getStart().plusMinutes(minutesNeeded);
                createdSlots.add(buildSlot(day, w.getStart(), slotEnd, goal));
                shrinkWindow(freeWindows, i, w, w.getStart(), slotEnd);
                return true;
            }
        }

        return false; // Step 8: didn't fit anywhere today
    }

    // After placing a slot inside window w, replace w with whatever time is left over (before/after)
    private void shrinkWindow(List<TimeWindow> freeWindows, int index, TimeWindow original,
                              LocalTime usedStart, LocalTime usedEnd) {
        freeWindows.remove(index);
        if (original.getStart().isBefore(usedStart)) {
            freeWindows.add(index, new TimeWindow(original.getStart(), usedStart));
            index++;
        }
        if (usedEnd.isBefore(original.getEnd())) {
            freeWindows.add(index, new TimeWindow(usedEnd, original.getEnd()));
        }
    }

    private TimetableSlot buildSlot(DayOfWeek day, LocalTime start, LocalTime end, Goal goal) {
        TimetableSlot slot = new TimetableSlot();
        slot.setDayOfWeek(day);
        slot.setStartTime(start);
        slot.setEndTime(end);
        slot.setGoal(goal);
        slot.setSource(SlotSource.AUTO_GENERATED);
        return slot;
    }

    private TimetableSlotResponse toResponse(TimetableSlot slot) {
        TimetableSlotResponse r = new TimetableSlotResponse();
        r.setId(slot.getId());
        r.setDayOfWeek(slot.getDayOfWeek());
        r.setStartTime(slot.getStartTime());
        r.setEndTime(slot.getEndTime());
        if (slot.getGoal() != null) {
            r.setGoalId(slot.getGoal().getId());
            r.setGoalTitle(slot.getGoal().getTitle());
        }
        r.setSource(slot.getSource().name());
        return r;
    }
}