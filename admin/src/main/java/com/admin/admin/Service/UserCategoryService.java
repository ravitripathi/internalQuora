package com.admin.admin.Service;

import com.admin.admin.Dto.UserCategoryDto;
import com.admin.admin.Entity.UserCategory;
import com.admin.admin.Repository.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserCategoryService {
    public boolean save(UserCategoryDto userCategoryDto);

    public List<UserCategory> findAll();

    public UserCategory findOne(String categoryId);
    public UserCategory findOneByUserId(String userId);
}
