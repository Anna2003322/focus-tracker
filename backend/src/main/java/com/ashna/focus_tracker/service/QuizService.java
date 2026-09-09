// service/QuizService.java
package com.ashna.focus_tracker.service;

import com.ashna.focus_tracker.dto.request.QuizSubmitRequest;
import com.ashna.focus_tracker.dto.response.QuizQuestionResponse;
import com.ashna.focus_tracker.dto.response.QuizResultResponse;
import com.ashna.focus_tracker.entity.QuizOption;
import com.ashna.focus_tracker.entity.QuizQuestion;
import com.ashna.focus_tracker.repository.QuizQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final UserStatsService userStatsService;

    public QuizService(QuizQuestionRepository quizQuestionRepository, UserStatsService userStatsService) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.userStatsService = userStatsService;
    }

    public List<QuizQuestionResponse> getRandomQuestions(int count) {
        List<QuizQuestion> all = quizQuestionRepository.findAll();
        Collections.shuffle(all);
        return all.stream().limit(count).map(this::toResponse).collect(Collectors.toList());
    }

    public QuizResultResponse submit(QuizSubmitRequest request) {
        Map<Long, String> answers = request.getAnswers();
        int correct = 0;

        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            QuizQuestion question = quizQuestionRepository.findById(entry.getKey()).orElse(null);
            if (question != null && question.getCorrectOption() == QuizOption.valueOf(entry.getValue())) {
                correct++;
            }
        }

        int coinsAwarded = correct * 5; // 5 coins per correct answer
        userStatsService.addCoins(coinsAwarded);

        QuizResultResponse result = new QuizResultResponse();
        result.setCorrectCount(correct);
        result.setTotalCount(answers.size());
        result.setCoinsAwarded(coinsAwarded);
        return result;
    }

    private QuizQuestionResponse toResponse(QuizQuestion q) {
        QuizQuestionResponse r = new QuizQuestionResponse();
        r.setId(q.getId());
        r.setQuestion(q.getQuestion());
        r.setOptionA(q.getOptionA());
        r.setOptionB(q.getOptionB());
        r.setOptionC(q.getOptionC());
        r.setOptionD(q.getOptionD());
        return r;
    }
}