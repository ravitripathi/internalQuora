package com.example.user.User.service.serviceImpl;

import com.example.user.User.APIContants;
import com.example.user.User.dto.CategoryList;
import com.example.user.User.entity.CategoryFollowMap;
import com.example.user.User.repository.CategoryRepository;
import com.example.user.User.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class CategoryRepositoryImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean add(CategoryFollowMap categoryFollowMap) {

        categoryRepository.save(categoryFollowMap);
        return true;
    }

    @Override
    public boolean remove(CategoryFollowMap categoryFollowMap) {
        categoryRepository.delete(categoryFollowMap);
        return true;
    }

    @Override
    public CategoryList findByCategory(String userId) {
        List<CategoryFollowMap> categoryFollowMaps = categoryRepository.findByCategoryFollowerMapIdentifierUserId(userId);
        CategoryList categoryLists = new CategoryList();
        ArrayList<String> list = new ArrayList<>();
        for (CategoryFollowMap categoryFollowMap : categoryFollowMaps) {
            list.add(categoryFollowMap.getCategoryFollowerMapIdentifier().getCategoryId());

        }
        categoryLists.setCategoryList(list);
        return categoryLists;
    }

    @Override
    public CategoryList findCategoryByUser(String categoryId) {
        List<CategoryFollowMap> categoryFollowMaps = categoryRepository.findByCategoryFollowerMapIdentifierCategoryId(categoryId);
        CategoryList categoryLists = new CategoryList();
        ArrayList<String> list = new ArrayList<>();
        for (CategoryFollowMap categoryFollowMap : categoryFollowMaps) {
            list.add(categoryFollowMap.getCategoryFollowerMapIdentifier().getUserId());

        }
        categoryLists.setCategoryList(list);
        return categoryLists;
    }

    @Override
    public ArrayList<String> findCategoryByUserKafka(String categoryId) {
        RestTemplate restTemplate = new RestTemplate();

        String id = restTemplate.getForObject(APIContants.ADMIN_SERVICE_API+"/getCategoryId/" + categoryId, String.class);

        List<CategoryFollowMap> categoryFollowMaps = categoryRepository.findByCategoryFollowerMapIdentifierCategoryId(id);
        CategoryList categoryLists = new CategoryList();
        ArrayList<String> list = new ArrayList<>();
        for (CategoryFollowMap categoryFollowMap : categoryFollowMaps) {
            list.add(categoryFollowMap.getCategoryFollowerMapIdentifier().getUserId());

        }

        return list;
    }

    @Override
    public ArrayList<String> findCategoryIdsByUserKafka(String userId) {
        List<CategoryFollowMap> categoryFollowMaps = categoryRepository.findByCategoryFollowerMapIdentifierUserId(userId);
        CategoryList categoryLists = new CategoryList();
        ArrayList<String> list = new ArrayList<>();
        for (CategoryFollowMap categoryFollowMap : categoryFollowMaps) {
            list.add(categoryFollowMap.getCategoryFollowerMapIdentifier().getCategoryId());

        }

        return list;
    }
}
