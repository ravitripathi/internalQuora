package com.example.user.User.dto;

import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.List;

public class CategoryList implements Serializable{
    private List<String> categoryList;

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }
}
