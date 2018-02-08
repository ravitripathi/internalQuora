package com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto;

public class ModeratorDTO {
    private String moderatorId;
    private String categoryUrl;
    public ModeratorDTO(String moderatorId, String categoryUrl) {
        this.moderatorId = moderatorId;
        this.categoryUrl = categoryUrl;
    }
    public String getModeratorId() {
        return moderatorId;
    }
    public void setModeratorId(String moderatorId) {
        this.moderatorId = moderatorId;
    }
    public String getCategoryUrl() {
        return categoryUrl;
    }
    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }
    public ModeratorDTO() {
    }
}