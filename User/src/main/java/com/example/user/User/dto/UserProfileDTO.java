package com.example.user.User.dto;

public class UserProfileDTO {
    private String userId;
    private String name;
    private String imageUrl;
    private int role;
    private int questions;
    private long followers;
    private long following;
    private long category;

    private int questionAnsweredCount;
    private int answerCount;
    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
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

    public UserProfileDTO(String userId, String name, String imageUrl, int role, int questions, long followers, long following, long category, int questionAnsweredCount, int answerCount) {
        this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.role = role;
        this.category = category;
        this.questions = questions;
        this.followers = followers;
        this.following = following;
        this.answerCount=answerCount;
        this.questionAnsweredCount=questionAnsweredCount;
    }

    public UserProfileDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public long getFollowing() {
        return following;
    }

    public void setFollowing(long following) {
        this.following = following;
    }
}
