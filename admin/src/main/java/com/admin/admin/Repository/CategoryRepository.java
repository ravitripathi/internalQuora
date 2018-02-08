package com.admin.admin.Repository;

import com.admin.admin.Dto.CategoryDto;
import com.admin.admin.Entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {

    public Category findOneByCategoryName(String categoryName);
}
