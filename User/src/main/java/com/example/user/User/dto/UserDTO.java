package com.example.user.User.dto;

public class UserDTO {
    private String userId;
    private String name;
    private String imageUrl;
    private int role;

    public UserDTO() {
    }

    public UserDTO(String userId, String name, String imageUrl, int role) {
        this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.role = role;
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
}
