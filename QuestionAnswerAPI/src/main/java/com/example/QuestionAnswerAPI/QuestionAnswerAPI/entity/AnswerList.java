package com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.AnswerDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = AnswerList.COLLECTION_NAME)
public class AnswerList {

    public static final String COLLECTION_NAME = "answer";

    @Id
    private String answerListId;    //id will be same as question id
    private List<Answer> answerDTOList;

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public String getAnswerListId() {
        return answerListId;
    }

    public void setAnswerListId(String answerListId) {
        this.answerListId = answerListId;
    }

    public List<Answer> getAnswerDTOList() {
        return answerDTOList;
    }

    public void setAnswerDTOList(List<Answer> answerDTOList) {
        this.answerDTOList = answerDTOList;
    }
}