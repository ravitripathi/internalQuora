package com.example.QuestionSearchService;

import com.example.QuestionSearchService.dto.QuestionDTO;
import com.example.QuestionSearchService.model.Question;
import com.example.QuestionSearchService.service.QuestionService;
import com.example.QuestionSearchService.service.impl.QuestionServiceImpl;
import org.apache.solr.client.solrj.SolrClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {

    @InjectMocks
    private QuestionServiceImpl questionServiceImpl;

    @Mock
    private QuestionService questionService;

    @Mock
    private SolrClient solrClient;




    private MockMvc mockMvc;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        // this.mockMvc= MockMvcBuilders.standaloneSetup(this.questionServiceImpl).build();

    }
    @After
    public void tearDown()
    {
        // Mockito.verifyNoMoreInteractions(questionService);
    }

    @Test
    public void testSave() throws Exception{
        QuestionDTO question= new QuestionDTO();
        question.setQuestionId("1");
        question.setTitle("Which is the most rated movie in 2017?");
        question.setCategory("Movie");
        List<String> tagList=  new ArrayList<>();
        tagList.add("Bahubali2");
        tagList.add("Bollywood");
        question.setTags(tagList);
        question.setUserName("divya");
        boolean isDataSave= questionServiceImpl.save(question);
        Assert.assertTrue(isDataSave);


    }


}
