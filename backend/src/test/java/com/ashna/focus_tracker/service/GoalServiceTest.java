package com.ashna.focus_tracker.service;

import com.ashna.focus_tracker.dto.request.GoalRequest;
import com.ashna.focus_tracker.dto.response.GoalResponse;
import com.ashna.focus_tracker.entity.CommitmentType;
import com.ashna.focus_tracker.entity.Goal;
import com.ashna.focus_tracker.entity.GoalType;
import com.ashna.focus_tracker.exception.ResourceNotFoundException;
import com.ashna.focus_tracker.mapper.GoalMapper;
import com.ashna.focus_tracker.repository.GoalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private GoalMapper goalMapper;

    @InjectMocks
    private GoalService goalService;

    @Test
    void getGoalById_returnsGoal_whenGoalExists() {
        Goal goal = new Goal();
        goal.setId(1L);
        goal.setTitle("CAT - Profit and Loss");

        GoalResponse response = new GoalResponse();
        response.setId(1L);
        response.setTitle("CAT - Profit and Loss");

        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));
        when(goalMapper.toResponse(goal)).thenReturn(response);

        GoalResponse result = goalService.getGoalById(1L);

        assertEquals("CAT - Profit and Loss", result.getTitle());
        verify(goalRepository, times(1)).findById(1L);
    }

    @Test
    void getGoalById_throwsException_whenGoalDoesNotExist() {
        when(goalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> goalService.getGoalById(99L));
    }

    @Test
    void createGoal_savesAndReturnsGoal() {
        GoalRequest request = new GoalRequest();
        request.setTitle("Job Hunting");
        request.setGoalType(GoalType.MAINTENANCE);
        request.setCommitmentMinutes(120);
        request.setCommitmentType(CommitmentType.DAILY);

        Goal unsaved = new Goal();
        unsaved.setTitle("Job Hunting");

        Goal saved = new Goal();
        saved.setId(2L);
        saved.setTitle("Job Hunting");

        GoalResponse response = new GoalResponse();
        response.setId(2L);
        response.setTitle("Job Hunting");

        when(goalMapper.toEntity(request)).thenReturn(unsaved);
        when(goalRepository.save(unsaved)).thenReturn(saved);
        when(goalMapper.toResponse(saved)).thenReturn(response);

        GoalResponse result = goalService.createGoal(request);

        assertEquals(2L, result.getId());
        assertEquals("Job Hunting", result.getTitle());
        verify(goalRepository, times(1)).save(unsaved);
    }

    @Test
    void deleteGoal_throwsException_whenGoalDoesNotExist() {
        when(goalRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> goalService.deleteGoal(5L));
        verify(goalRepository, never()).delete(any());
    }
}