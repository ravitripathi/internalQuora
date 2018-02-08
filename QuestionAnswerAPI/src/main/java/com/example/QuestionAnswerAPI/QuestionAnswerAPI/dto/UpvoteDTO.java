package com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto;

import java.util.List;

public class UpvoteDTO {
    private String upvoteId;    //id will be same as answer id
    private String userId;

    public String getUpvoteId() {
        return upvoteId;
    }

    public void setUpvoteId(String upvoteId) {
        this.upvoteId = upvoteId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UpvoteDTO{" +
                "upvoteId='" + upvoteId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
