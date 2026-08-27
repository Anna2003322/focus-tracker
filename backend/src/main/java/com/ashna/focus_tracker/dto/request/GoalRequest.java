package com.ashna.focus_tracker.dto.request;

import com.ashna.focus_tracker.entity.CommitmentType;
import com.ashna.focus_tracker.entity.GoalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public class GoalRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String category;

    @NotNull(message = "Goal type is required")
    private GoalType goalType;

    private LocalTime preferredStartTime;
    private LocalTime preferredEndTime;

    @NotNull(message = "Commitment minutes is required")
    @Positive(message = "Commitment minutes must be positive")
    private Integer commitmentMinutes;

    @NotNull(message = "Commitment type is required")
    private CommitmentType commitmentType;

    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public LocalTime getPreferredStartTime() {
        return preferredStartTime;
    }

    public void setPreferredStartTime(LocalTime preferredStartTime) {
        this.preferredStartTime = preferredStartTime;
    }

    public LocalTime getPreferredEndTime() {
        return preferredEndTime;
    }

    public void setPreferredEndTime(LocalTime preferredEndTime) {
        this.preferredEndTime = preferredEndTime;
    }

    public Integer getCommitmentMinutes() {
        return commitmentMinutes;
    }

    public void setCommitmentMinutes(Integer commitmentMinutes) {
        this.commitmentMinutes = commitmentMinutes;
    }

    public CommitmentType getCommitmentType() {
        return commitmentType;
    }

    public void setCommitmentType(CommitmentType commitmentType) {
        this.commitmentType = commitmentType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}