package com.admin.admin.Service;

import com.admin.admin.Dto.CategoryDto;
import com.admin.admin.Dto.UserCategoryDto;
import com.admin.admin.Entity.Category;
import com.admin.admin.Entity.UserCategory;
import com.admin.admin.Repository.UserCategoryRepository;
import com.admin.admin.Service.Impl.UserCategoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserCategoryServiceImplTest {
    @InjectMocks
    private UserCategoryServiceImpl userCategoryServiceImpl;

    @Mock
    private UserCategoryRepository userCategoryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testgetAllModerators() throws Exception {
        List<UserCategory> moderatorList = new ArrayList<>();
        moderatorList.add(new UserCategory("1", "sweta.sharma@coviam.com"));
        moderatorList.add(new UserCategory("2", "abc@gmail.com"));
        when(userCategoryRepository.findAll()).thenReturn(moderatorList);
        List<UserCategory>Response=userCategoryServiceImpl.findAll();
        Assert.assertEquals(moderatorList,Response);
    }

    @Test
    public void testgetOne() throws Exception {
        UserCategory userCategory = new UserCategory();
        userCategory.setCategoryId("1");
        userCategory.setUserId("abc@gmail.com");
        when(userCategoryRepository.findOne("1")).thenReturn(userCategory);
        UserCategory response=userCategoryServiceImpl.findOne("1");
        Assert.assertEquals(userCategory,response);
    }

    @Test
    public void testsave() throws Exception {

        UserCategoryDto userCategoryDto=new UserCategoryDto();
        userCategoryDto.setCategoryId("1");
        userCategoryDto.setUserId("divya25sen@gmail.com");

        UserCategory userCategory=new UserCategory();
        BeanUtils.copyProperties(userCategoryDto,userCategory);


        Mockito.when(userCategoryRepository.save(userCategory)).thenReturn(userCategory);
        boolean response=userCategoryServiceImpl.save(userCategoryDto);
        System.out.print(response);
        Assert.assertTrue(response);

    }
}
