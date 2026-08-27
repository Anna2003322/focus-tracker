package com.ashna.focus_tracker.service;

import com.ashna.focus_tracker.dto.request.GoalRequest;
import com.ashna.focus_tracker.dto.response.GoalResponse;
import com.ashna.focus_tracker.entity.Goal;
import com.ashna.focus_tracker.exception.ResourceNotFoundException;
import com.ashna.focus_tracker.mapper.GoalMapper;
import com.ashna.focus_tracker.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;

    public GoalService(GoalRepository goalRepository, GoalMapper goalMapper) {
        this.goalRepository = goalRepository;
        this.goalMapper = goalMapper;
    }

    public List<GoalResponse> getAllGoals() {
        return goalRepository.findAll().stream()
                .map(goalMapper::toResponse)
                .collect(Collectors.toList());
    }

    public GoalResponse getGoalById(Long id) {
        Goal goal = findGoalOrThrow(id);
        return goalMapper.toResponse(goal);
    }

    public GoalResponse createGoal(GoalRequest request) {
        Goal goal = goalMapper.toEntity(request);
        Goal saved = goalRepository.save(goal);
        return goalMapper.toResponse(saved);
    }

    public GoalResponse updateGoal(Long id, GoalRequest request) {
        Goal existing = findGoalOrThrow(id);

        existing.setTitle(request.getTitle());
        existing.setCategory(request.getCategory());
        existing.setGoalType(request.getGoalType());
        existing.setPreferredStartTime(request.getPreferredStartTime());
        existing.setPreferredEndTime(request.getPreferredEndTime());
        existing.setCommitmentMinutes(request.getCommitmentMinutes());
        existing.setCommitmentType(request.getCommitmentType());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        existing.setNotes(request.getNotes());

        Goal updated = goalRepository.save(existing);
        return goalMapper.toResponse(updated);
    }

    public void deleteGoal(Long id) {
        Goal goal = findGoalOrThrow(id);
        goalRepository.delete(goal);
    }

    private Goal findGoalOrThrow(Long id) {
        return goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id " + id));
    }
}