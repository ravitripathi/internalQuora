package com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto;

public class slack {
    String text;

    public String getText() {
        return text;
    }

    public slack() {
    }

    public slack(String text) {

        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
