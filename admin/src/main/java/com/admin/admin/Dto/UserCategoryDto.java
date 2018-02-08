package com.admin.admin.Dto;

public class UserCategoryDto {
    private String categoryId;
    private String userId;

    public UserCategoryDto() {
    }

    public UserCategoryDto(String categoryId, String userId) {

        this.categoryId = categoryId;
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
