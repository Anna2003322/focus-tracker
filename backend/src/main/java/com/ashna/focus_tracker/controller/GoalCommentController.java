package com.ashna.focus_tracker.controller;

import com.ashna.focus_tracker.dto.request.CommentRequest;
import com.ashna.focus_tracker.dto.response.CommentResponse;
import com.ashna.focus_tracker.service.GoalCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/goals/{goalId}/comments")
public class GoalCommentController {

    private final GoalCommentService commentService;

    public GoalCommentController(GoalCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getAll(@PathVariable Long goalId) {
        return commentService.getForGoal(goalId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse add(@PathVariable Long goalId, @RequestBody CommentRequest request) {
        return commentService.add(goalId, request);
    }
}