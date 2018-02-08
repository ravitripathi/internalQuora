package com.example.user.User.dto;

public class ProfileCountingDTO {
    private String userId;
    private int questionAskedCount;
    private int questionAnsweredCount;
    private int answerCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQuestionAskedCount() {
        return questionAskedCount;
    }

    public void setQuestionAskedCount(int questionAskedCount) {
        this.questionAskedCount = questionAskedCount;
    }

    public int getQuestionAnsweredCount() {
        return questionAnsweredCount;
    }

    public void setQuestionAnsweredCount(int questionAnsweredCount) {
        this.questionAnsweredCount = questionAnsweredCount;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }
}
