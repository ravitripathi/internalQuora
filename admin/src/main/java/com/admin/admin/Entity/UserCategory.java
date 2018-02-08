package com.admin.admin.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = UserCategory.TABLE_NAME)
public class UserCategory {
    public static final String TABLE_NAME = "user_category";
    @Id
    private String categoryId;
    private String userId;

    public UserCategory() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCategory that = (UserCategory) o;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(categoryId, userId);
    }

    public UserCategory(String categoryId, String userId) {

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
