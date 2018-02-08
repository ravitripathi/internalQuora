package com.example.user.User.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Embeddable
public class CategoryFollowerMapIdentifier implements Serializable {
    @NotNull
    private String userId;
    @NotNull
    private String categoryId;

    public CategoryFollowerMapIdentifier() {
    }

    public CategoryFollowerMapIdentifier(String userId, String categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
