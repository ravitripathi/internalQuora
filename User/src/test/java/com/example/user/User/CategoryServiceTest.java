package com.example.user.User;

import com.example.user.User.dto.CategoryList;
import com.example.user.User.entity.CategoryFollowMap;
import com.example.user.User.entity.CategoryFollowerMapIdentifier;
import com.example.user.User.entity.User;
import com.example.user.User.repository.CategoryRepository;
import com.example.user.User.repository.UserRepository;
import com.example.user.User.service.CategoryService;
import com.example.user.User.service.UserService;
import com.example.user.User.service.serviceImpl.CategoryRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class CategoryServiceTest{
    @InjectMocks
    private CategoryRepositoryImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void findByCategoryTest() {

        List<CategoryFollowMap> catList  = new ArrayList<>();
        catList.add(new CategoryFollowMap(new CategoryFollowerMapIdentifier("sahilu01@gmail.com","ii")));
        Mockito.when(categoryRepository.findByCategoryFollowerMapIdentifierUserId("sahilu01@gmail.com")).thenReturn(catList);
        CategoryList response = categoryService.findByCategory("sahilu01@gmail.com");
        Assert.assertEquals(response.getCategoryList().get(0), "ii");


    }

    @Test
    public  void findCategoryByUserTest(){
        List<CategoryFollowMap> categoryFollowMapList = new ArrayList<>();
        categoryFollowMapList.add(new CategoryFollowMap(new CategoryFollowerMapIdentifier("sa","6278364")));
        Mockito.when(categoryRepository.findByCategoryFollowerMapIdentifierCategoryId("6278364")).thenReturn(categoryFollowMapList) ;
        CategoryList response = categoryService.findCategoryByUser("6278364");
        System.out.println(response.getCategoryList().toString());
        Assert.assertEquals(response.getCategoryList().get(0),"sa");
    }

    @Test
    public void findCategoryByUserKafkaTest(){
        List<CategoryFollowMap> catList  = new ArrayList<>();
        catList.add(new CategoryFollowMap(new CategoryFollowerMapIdentifier("sahilu01@gmail.com","ii")));
        Mockito.when(categoryRepository.findByCategoryFollowerMapIdentifierCategoryId("sahilu01@gmail.com")).thenReturn(catList);
        ArrayList<String> response = categoryService.findCategoryByUserKafka("sahilu01@gmail.com");
        Assert.assertEquals(response.get(0),"sahilu01@gmail.com");


    }

    @Test
    public void findCategoryIdsByUserKafka(){
        List<CategoryFollowMap> catList  = new ArrayList<>();
        catList.add(new CategoryFollowMap(new CategoryFollowerMapIdentifier("sahilu01@gmail.com","ii")));
        Mockito.when(categoryRepository.findByCategoryFollowerMapIdentifierUserId("ii")).thenReturn(catList);
        ArrayList<String> response = categoryService.findCategoryIdsByUserKafka("ii");
        Assert.assertEquals(response.get(0),"ii");


    }

    @Test
    public void removeTest(){
        CategoryFollowMap categoryFollowMap = new CategoryFollowMap();
        categoryFollowMap.setCategoryFollowerMapIdentifier(new CategoryFollowerMapIdentifier("fshgd","e237tgbdx"));
        boolean response = categoryService.remove(categoryFollowMap);
        Assert.assertEquals(response,true);

    }


}
