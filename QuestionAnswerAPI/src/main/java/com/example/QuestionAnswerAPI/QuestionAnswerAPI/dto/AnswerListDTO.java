package com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto;

import java.util.List;

public class AnswerListDTO {
    private String answerListId;
    private List<AnswerDTO> answerDTOList;

    public AnswerListDTO() {
    }

    public AnswerListDTO(String answerListId, List<AnswerDTO> answerDTOList) {
        this.answerListId = answerListId;
        this.answerDTOList = answerDTOList;
    }

    public String getAnswerListId() {
        return answerListId;
    }

    public void setAnswerListId(String answerListId) {
        this.answerListId = answerListId;
    }

    public List<AnswerDTO> getAnswerDTOList() {
        return answerDTOList;
    }

    public void setAnswerDTOList(List<AnswerDTO> answerDTOList) {
        this.answerDTOList = answerDTOList;
    }
}
