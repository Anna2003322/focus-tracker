package com.ashna.focus_tracker.dto.request;

import java.util.Map;

public class QuizSubmitRequest {
    // key: questionId, value: chosen option letter ("A"/"B"/"C"/"D")
    private Map<Long, String> answers;

    public Map<Long, String> getAnswers() { return answers; }
    public void setAnswers(Map<Long, String> answers) { this.answers = answers; }
}