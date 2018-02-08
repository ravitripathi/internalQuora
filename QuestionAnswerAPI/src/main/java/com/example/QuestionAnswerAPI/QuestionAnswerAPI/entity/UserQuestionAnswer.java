package com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = UserQuestionAnswer.COLLECTION_NAME)
public class UserQuestionAnswer {
    public static final String COLLECTION_NAME = "userAnswers";

    @Id
    private String userId;
    private List<String> questionIdList;
    private List<String> answerIdList;

    public List<String> getQuestionIdList() {
        return questionIdList;
    }

    public void setQuestionIdList(List<String> questionIdList) {
        this.questionIdList = questionIdList;
    }

    public List<String> getAnswerIdList() {
        return answerIdList;
    }

    public void setAnswerIdList(List<String> answerIdList) {
        this.answerIdList = answerIdList;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
