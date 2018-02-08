package com.admin.admin.Service;

import com.admin.admin.Dto.CategoryDto;
import com.admin.admin.Dto.ModeratorDto;
import com.admin.admin.Entity.Category;

import java.util.List;

public interface CategoryService {
    public boolean save(CategoryDto categoryDto);

    public List<CategoryDto> findAll();

    public Category findOne(String categoryId);
    public String findNameById(String caegoryId);
    public ModeratorDto findModeratorInfo(String categoryName);

    public List<String> findEach(List<String> categoryId);

    public CategoryDto findCategoryByModeratorId(String userId);
    public String findCategoryId(String categoryName);
}
