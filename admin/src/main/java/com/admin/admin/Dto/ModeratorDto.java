package com.admin.admin.Dto;

public class ModeratorDto {
    private String moderatorId;
    private String categoryUrl;

    public ModeratorDto(String moderatorId, String categoryUrl) {

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

    public ModeratorDto() {
    }


}
