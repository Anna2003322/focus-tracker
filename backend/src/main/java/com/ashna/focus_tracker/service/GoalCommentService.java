package com.ashna.focus_tracker.service;

import com.ashna.focus_tracker.dto.request.CommentRequest;
import com.ashna.focus_tracker.dto.response.CommentResponse;
import com.ashna.focus_tracker.entity.Goal;
import com.ashna.focus_tracker.entity.GoalComment;
import com.ashna.focus_tracker.exception.ResourceNotFoundException;
import com.ashna.focus_tracker.repository.GoalCommentRepository;
import com.ashna.focus_tracker.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalCommentService {

    private final GoalCommentRepository commentRepository;
    private final GoalRepository goalRepository;

    public GoalCommentService(GoalCommentRepository commentRepository, GoalRepository goalRepository) {
        this.commentRepository = commentRepository;
        this.goalRepository = goalRepository;
    }

    public List<CommentResponse> getForGoal(Long goalId) {
        Goal goal = findGoalOrThrow(goalId);
        return commentRepository.findByGoalOrderByCreatedAtDesc(goal).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    public CommentResponse add(Long goalId, CommentRequest request) {
        Goal goal = findGoalOrThrow(goalId);
        GoalComment comment = new GoalComment();
        comment.setGoal(goal);
        comment.setText(request.getText());
        return toResponse(commentRepository.save(comment));
    }

    private Goal findGoalOrThrow(Long goalId) {
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id " + goalId));
    }

    private CommentResponse toResponse(GoalComment comment) {
        CommentResponse r = new CommentResponse();
        r.setId(comment.getId());
        r.setText(comment.getText());
        r.setCreatedAt(comment.getCreatedAt());
        return r;
    }
}