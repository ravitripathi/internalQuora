package com.admin.admin.Repository;

import com.admin.admin.Entity.UserCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCategoryRepository extends CrudRepository<UserCategory, String> {
    public UserCategory findOneByUserId(String userId);
}
