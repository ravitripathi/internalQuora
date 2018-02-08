package com.admin.admin.Controller;

import com.admin.admin.Dto.CategoryDto;
import com.admin.admin.Dto.ModeratorDto;
import com.admin.admin.Dto.UserCategoryDto;
import com.admin.admin.Entity.Category;
import com.admin.admin.Entity.UserCategory;
import com.admin.admin.Repository.CategoryRepository;
import com.admin.admin.Service.CategoryService;
import com.admin.admin.Service.Impl.CategoryServiceImpl;
import com.admin.admin.Service.UserCategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private UserCategoryService userCategoryService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    private MockMvc mockMvc;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.categoryController).build();
    }

//    @After
//    public void tearDown() {
//        Mockito.verifyNoMoreInteractions(userCategoryService);
//    }


    @Test
    public void testgetAll() throws Exception {
        List<CategoryDto> categoryListDto = new ArrayList<>();
        categoryListDto.add(new CategoryDto("1", "General", "http://coviam.com/img/coviam.jpg",3));
        categoryListDto.add(new CategoryDto("2", "Food", "https://zzoob.com/wp-content/uploads/2017/08/585be1aa1600002400bdf2a6.jpeg",5));
        when(categoryService.findAll()).thenReturn(categoryListDto);
        this.mockMvc.perform(get("/getAll").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(categoryService).findAll();
    }

    @Test
    public void testgetCategoryDetails() throws Exception {
        Category category = new Category();
        category.setCategoryId("id");
        when(categoryService.findOne("id")).thenReturn(category);
        this.mockMvc.perform(get("/getCategoryDetails/{categoryId}", "id"))
                .andExpect(status().isOk()).andExpect(jsonPath("categoryId", equalTo("id")))
                .andExpect(jsonPath("categoryName", nullValue()));
        verify(categoryService).findOne("id");
    }

    @Test
    public void testgetAllModerators() throws Exception {
        List<UserCategory> moderatorList = new ArrayList<>();
        moderatorList.add(new UserCategory("1", "sweta.sharma@coviam.com"));
        moderatorList.add(new UserCategory("2", "abc@gmail.com"));
        when(userCategoryService.findAll()).thenReturn(moderatorList);
        this.mockMvc.perform(get("/getAllModerators").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userCategoryService).findAll();
    }

    @Test
    public void testgetOne() throws Exception {
        UserCategory userCategory = new UserCategory();
        userCategory.setCategoryId("id");
        when(userCategoryService.findOne("id")).thenReturn(userCategory);
        this.mockMvc.perform(get("/getOne/{categoryId}", "id"))
                .andExpect(status().isOk()).andExpect(jsonPath("categoryId", equalTo("id")))
                .andExpect(jsonPath("userId", nullValue()));
        verify(userCategoryService).findOne("id");
    }

    @Test
    public void testsave() throws Exception {
    when(categoryService.save(Matchers.any(CategoryDto.class))).thenReturn(true);

       mockMvc.perform(post("/saveCategory").contentType(MediaType.APPLICATION_JSON)
               .content("{ \"categoryId\": \"1\", \"categoryName\": \"General\",\"imageUrl\":\"http://coviam.com/img/coviam.jpg\"}")).andExpect(status()
               .isOk());
   }

   @Test
    public void testgetCategoryList() throws Exception{
        List<String> categoryNames=new ArrayList<>();
        List<String> categoryIds=new ArrayList<>();
        categoryIds.add("id");
        categoryIds.add("id2");
        categoryNames.add("General");
        categoryNames.add("Food");
        when(categoryService.findEach(categoryIds)).thenReturn(categoryNames);
        mockMvc.perform(post("/getCategoryList").contentType(MediaType.APPLICATION_JSON)
        .content("[\"id\",\"id2\"]")).andExpect(status().isOk());
   }
    @Test
    public void testsaveModeartor() throws Exception {
        when(userCategoryService.save(Matchers.any(UserCategoryDto.class))).thenReturn(true);

        mockMvc.perform(post("/saveModerator").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"categoryId\": \"1\", \"userId\": \"divya25sen@gmail.com\"}")).andExpect(status()
                .isOk());
//        Mockito.verify(userCategoryService).save()
    }
    @Test
    public void testgetModeratorInfo() throws Exception {
        Category category=new Category();
        category.setCategoryName("name");
        ModeratorDto moderatorDto=new ModeratorDto();
        when(categoryService.findModeratorInfo("name")).thenReturn(moderatorDto);
        this.mockMvc.perform(get("/getModeratorInfo/{categoryName}", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("moderatorId", nullValue()));
        verify(categoryService).findModeratorInfo("name");
    }

    @Test
    public void testgetCategoryId() throws Exception
    {
        Category category=new Category();
        category.setCategoryName("name");
        category.setCategoryId("id");
        category.setImageUrl("imageUrl");
        when(categoryService.findCategoryId("name")).thenReturn(category.getCategoryId());
        this.mockMvc.perform(get("/getCategoryId/{categoryName}", "name"))
                .andExpect(status().isOk()).andExpect(content().string(category.getCategoryId()));
        verify(categoryService).findCategoryId("name");
    }
//    @Test
//    public void testgetCategoryByModerator() throws Exception
//    {
//      CategoryDto categoryDto=new CategoryDto();
//      UserCategory userCategory=new UserCategory();
//      userCategory.setUserId("userId");
//      userCategory.setCategoryId("id");
//      when(categoryService.findCategoryByModeratorId("userId")).thenReturn(categoryDto);
//      this.mockMvc.perform(post("/getCategoryByModerator/").contentType(userCategory.getUserId())
//              .content("{ \"userId\": \"divya25sen@gmail.com\"}")).andExpect(status()
//            .isOk());;
////      Mockito.verify(categoryService.findCategoryByModeratorId("userId"));
//    }
}

