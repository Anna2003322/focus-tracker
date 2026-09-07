package com.ashna.focus_tracker.scheduling;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeWindow {
    private LocalTime start;
    private LocalTime end;

    public TimeWindow(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }

    public long durationMinutes() {
        return ChronoUnit.MINUTES.between(start, end);
    }
}