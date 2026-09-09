package com.ashna.focus_tracker.dto.response;

public class QuizResultResponse {
    private int correctCount;
    private int totalCount;
    private int coinsAwarded;

    public int getCorrectCount() { return correctCount; }
    public void setCorrectCount(int correctCount) { this.correctCount = correctCount; }
    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
    public int getCoinsAwarded() { return coinsAwarded; }
    public void setCoinsAwarded(int coinsAwarded) { this.coinsAwarded = coinsAwarded; }
}