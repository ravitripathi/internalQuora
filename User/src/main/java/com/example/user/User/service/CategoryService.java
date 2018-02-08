package com.example.user.User.service;

import com.example.user.User.dto.CategoryList;
import com.example.user.User.entity.CategoryFollowMap;
import com.example.user.User.entity.FollowerMap;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {

    public boolean add(CategoryFollowMap categoryFollowMap);

    public boolean remove(CategoryFollowMap categoryFollowMap);

    public CategoryList findByCategory(String userId);

    public CategoryList findCategoryByUser(String categoryId);

    public ArrayList<String> findCategoryByUserKafka(String categoryId);
    public ArrayList<String> findCategoryIdsByUserKafka(String userId);
}
