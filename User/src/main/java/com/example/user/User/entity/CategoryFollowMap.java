package com.example.user.User.entity;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = CategoryFollowMap.TABLE_NAME)
public class CategoryFollowMap {
    public static final String TABLE_NAME = "category_follow_map";

    public CategoryFollowMap() {
    }

    public CategoryFollowMap(CategoryFollowerMapIdentifier categoryFollowerMapIdentifier) {
        this.categoryFollowerMapIdentifier = categoryFollowerMapIdentifier;
    }

    @EmbeddedId
    private CategoryFollowerMapIdentifier categoryFollowerMapIdentifier;

    public static String getTableName() {
        return TABLE_NAME;
    }

    public CategoryFollowerMapIdentifier getCategoryFollowerMapIdentifier() {
        return categoryFollowerMapIdentifier;
    }

    public void setCategoryFollowerMapIdentifier(CategoryFollowerMapIdentifier categoryFollowerMapIdentifier) {
        this.categoryFollowerMapIdentifier = categoryFollowerMapIdentifier;
    }
}
