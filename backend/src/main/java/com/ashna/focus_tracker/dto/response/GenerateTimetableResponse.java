package com.ashna.focus_tracker.dto.response;

import java.util.List;

public class GenerateTimetableResponse {
    private List<TimetableSlotResponse> slots;
    private String warning;

    public List<TimetableSlotResponse> getSlots() { return slots; }
    public void setSlots(List<TimetableSlotResponse> slots) { this.slots = slots; }
    public String getWarning() { return warning; }
    public void setWarning(String warning) { this.warning = warning; }
}