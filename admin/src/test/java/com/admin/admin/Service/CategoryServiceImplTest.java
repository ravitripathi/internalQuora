package com.admin.admin.Service;

import com.admin.admin.Dto.CategoryDto;
import com.admin.admin.Dto.UserCategoryDto;
import com.admin.admin.Entity.Category;
import com.admin.admin.Entity.UserCategory;
import com.admin.admin.Repository.CategoryRepository;
import com.admin.admin.Repository.UserCategoryRepository;
import com.admin.admin.Service.Impl.CategoryServiceImpl;
import com.admin.admin.Service.Impl.UserCategoryServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("1", "General", "http://coviam.com/img/coviam.jpg"));
        categoryList.add(new Category("2", "Food", "https://zzoob.com/wp-content/uploads/2017/08/585be1aa1600002400bdf2a6.jpeg"));
        when(categoryRepository.findAll()).thenReturn(categoryList);
     //   List<Category> response = categoryServiceImpl.findAll();
       // Assert.assertEquals(categoryList, response);
    }

    @Test
    public void testgetCategoryDetails() throws Exception {
        Category category = new Category();
        category.setCategoryId("1");
        category.setCategoryName("General");
        category.setImageUrl("http://coviam.com/img/coviam.jpg");
        when(categoryRepository.findOne("1")).thenReturn(category);
        Category response=categoryServiceImpl.findOne("1");
        Assert.assertEquals(category,response);
    }

   @Test
   public void testsave() throws Exception {

        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setCategoryId("1");
        categoryDto.setCategoryName("General");
        categoryDto.setImageUrl("http://coviam.com/img/coviam.jpg");
        Category category=new Category();
       BeanUtils.copyProperties(categoryDto,category);


      Mockito.when(categoryRepository.save(category)).thenReturn(category);
         boolean response=categoryServiceImpl.save(categoryDto);
         System.out.print(response);
         Assert.assertTrue(response);

    }


}
