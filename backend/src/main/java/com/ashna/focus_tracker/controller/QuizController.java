package com.ashna.focus_tracker.controller;

import com.ashna.focus_tracker.dto.request.QuizSubmitRequest;
import com.ashna.focus_tracker.dto.response.QuizQuestionResponse;
import com.ashna.focus_tracker.dto.response.QuizResultResponse;
import com.ashna.focus_tracker.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/random")
    public List<QuizQuestionResponse> random(@RequestParam(defaultValue = "5") int count) {
        return quizService.getRandomQuestions(count);
    }

    @PostMapping("/submit")
    public QuizResultResponse submit(@RequestBody QuizSubmitRequest request) {
        return quizService.submit(request);
    }
}