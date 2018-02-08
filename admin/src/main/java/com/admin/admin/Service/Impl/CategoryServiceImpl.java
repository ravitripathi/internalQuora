package com.admin.admin.Service.Impl;

import com.admin.admin.Dto.CategoryDto;
import com.admin.admin.Dto.Comparator;
import com.admin.admin.Dto.ModeratorDto;
import com.admin.admin.Entity.Category;
import com.admin.admin.Entity.UserCategory;
import com.admin.admin.Repository.CategoryRepository;
import com.admin.admin.Repository.UserCategoryRepository;
import com.admin.admin.Service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Override
    public boolean save(CategoryDto categoryDto) {

        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        Category temp = categoryRepository.save(category);
        if (temp == null)
            return false;
        else
            return true;
    }


    @Override
    public List<CategoryDto> findAll() {

        List<Category> categoryList = (List<Category>) categoryRepository.findAll();
        List<CategoryDto> categoryListDto=new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        for (Category category : categoryList) {
            int numberOfQuestions = restTemplate.getForObject("http://192.168.43.60:8080/questionAnswer/countQuestionByCategory/" + category.getCategoryName(), Integer.class);
            CategoryDto categoryDto = new CategoryDto();

            BeanUtils.copyProperties(category, categoryDto);
            categoryDto.setNumberOfQuestions(numberOfQuestions);
            categoryListDto.add(categoryDto);
        }
        Collections.sort(categoryListDto,new Comparator());

        return categoryListDto;
    }

    @Override
    public Category findOne(String categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    @Override
    public String findNameById(String caegoryId) {
        String categoryName=new String();
        Category category=new Category();
        category=categoryRepository.findOne(caegoryId);
        categoryName=category.getCategoryName();
        return categoryName;
    }

    @Override
    public List<String> findEach(List<String> categoryId) {
        List<String> categorynames = new ArrayList<String>();
        for (String catId : categoryId) {
            Category category = new Category();
            category = categoryRepository.findOne(catId);
            CategoryDto categoryDto = new CategoryDto();

            BeanUtils.copyProperties(category, categoryDto);
            categorynames.add(categoryDto.getCategoryName());
        }
        return categorynames;
    }

    @Override
    public String findCategoryId(String categoryName) {
        Category category = categoryRepository.findOneByCategoryName(categoryName);
        return category.getCategoryId();
    }

    @Override
    public ModeratorDto findModeratorInfo(String categoryName) {
        Category category = categoryRepository.findOneByCategoryName(categoryName);
        ModeratorDto moderatorDto = new ModeratorDto();
        moderatorDto.setCategoryUrl(category.getImageUrl());
        UserCategory userCategory = userCategoryRepository.findOne(category.getCategoryId());
        moderatorDto.setModeratorId(userCategory.getUserId());
        return moderatorDto;
    }

    @Override
    public CategoryDto findCategoryByModeratorId(String userId) {
        CategoryDto categoryDto = new CategoryDto();
        if (userCategoryRepository.findOneByUserId(userId) == null) {
            categoryDto.setCategoryName("null");
            categoryDto.setCategoryId("-1");
            categoryDto.setImageUrl("");
            return categoryDto;
        } else {
            UserCategory userCategory = userCategoryRepository.findOneByUserId(userId);

            Category category = new Category();
            category = categoryRepository.findOne(userCategory.getCategoryId());
            BeanUtils.copyProperties(category, categoryDto);
            return categoryDto;
        }
    }
}
