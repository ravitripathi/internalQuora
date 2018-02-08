package com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = UpvoteList.COLLECTION_NAME)
public class UpvoteList {

    public static final String COLLECTION_NAME = "upvote";

    @Id
    private String upvoteListId;    //id will be same as answer id
    private List<String> userIdList;

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public String getUpvoteListId() {
        return upvoteListId;
    }

    public void setUpvoteListId(String upvoteListId) {
        this.upvoteListId = upvoteListId;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
