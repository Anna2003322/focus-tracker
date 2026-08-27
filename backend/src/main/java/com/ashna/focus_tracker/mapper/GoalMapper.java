package com.ashna.focus_tracker.mapper;

import com.ashna.focus_tracker.dto.request.GoalRequest;
import com.ashna.focus_tracker.dto.response.GoalResponse;
import com.ashna.focus_tracker.entity.Goal;
import org.springframework.stereotype.Component;

@Component
public class GoalMapper {

    public Goal toEntity(GoalRequest request) {
        Goal goal = new Goal();
        goal.setTitle(request.getTitle());
        goal.setCategory(request.getCategory());
        goal.setGoalType(request.getGoalType());
        goal.setPreferredStartTime(request.getPreferredStartTime());
        goal.setPreferredEndTime(request.getPreferredEndTime());
        goal.setCommitmentMinutes(request.getCommitmentMinutes());
        goal.setCommitmentType(request.getCommitmentType());
        goal.setStartDate(request.getStartDate());
        goal.setEndDate(request.getEndDate());
        goal.setNotes(request.getNotes());
        return goal;
    }

    public GoalResponse toResponse(Goal goal) {
        GoalResponse response = new GoalResponse();
        response.setId(goal.getId());
        response.setTitle(goal.getTitle());
        response.setCategory(goal.getCategory());
        response.setGoalType(goal.getGoalType());
        response.setPreferredStartTime(goal.getPreferredStartTime());
        response.setPreferredEndTime(goal.getPreferredEndTime());
        response.setCommitmentMinutes(goal.getCommitmentMinutes());
        response.setCommitmentType(goal.getCommitmentType());
        response.setStartDate(goal.getStartDate());
        response.setEndDate(goal.getEndDate());
        response.setNotes(goal.getNotes());
        response.setStatus(goal.getStatus());
        response.setCreatedAt(goal.getCreatedAt());
        return response;
    }
}