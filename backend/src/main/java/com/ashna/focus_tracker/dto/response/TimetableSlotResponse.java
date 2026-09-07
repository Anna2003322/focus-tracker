package com.ashna.focus_tracker.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimetableSlotResponse {
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long goalId;
    private String goalTitle;
    private String source;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public Long getGoalId() { return goalId; }
    public void setGoalId(Long goalId) { this.goalId = goalId; }
    public String getGoalTitle() { return goalTitle; }
    public void setGoalTitle(String goalTitle) { this.goalTitle = goalTitle; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}