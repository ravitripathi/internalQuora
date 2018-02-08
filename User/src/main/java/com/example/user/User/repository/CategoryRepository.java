package com.example.user.User.repository;

import com.example.user.User.entity.CategoryFollowMap;
import com.example.user.User.entity.CategoryFollowerMapIdentifier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryFollowMap,CategoryFollowerMapIdentifier> {
    long countByCategoryFollowerMapIdentifierUserId(String userId);
    List<CategoryFollowMap> findByCategoryFollowerMapIdentifierUserId(String userId);
    List<CategoryFollowMap> findByCategoryFollowerMapIdentifierCategoryId(String categoryId);
}
