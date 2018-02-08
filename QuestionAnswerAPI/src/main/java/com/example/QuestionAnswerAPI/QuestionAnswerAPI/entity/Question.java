package com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = Question.COLLECTION_NAME)
public class Question {
    public static final String COLLECTION_NAME = "question";

    @Id
    private String questionId;
    private String title;
    private String userId;
    private String content;
    private String timestamp;
    private boolean active;
    private String category;
    private List<String> tags;
    private String imageUrl;
    private String userName;
//    private String categoryUrl;
//    private String moderatorId;
//
//    public String getCategoryUrl() {
//        return categoryUrl;
//    }
//
//    public void setCategoryUrl(String categoryUrl) {
//        this.categoryUrl = categoryUrl;
//    }
//
//    public String getModeratorId() {
//        return moderatorId;
//    }
//
//    public void setModeratorId(String moderatorId) {
//        this.moderatorId = moderatorId;
//    }

    public Question() {

    }

    public Question(String questionId, String title, String userId, String content, String timestamp, boolean active, String category, List<String> tags, String imageUrl, String userName) {
        this.questionId = questionId;
        this.title = title;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
        this.active = active;
        this.category = category;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
