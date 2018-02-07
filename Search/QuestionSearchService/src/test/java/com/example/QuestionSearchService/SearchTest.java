package com.example.QuestionSearchService;

import com.example.QuestionSearchService.controller.QuestionController;
import com.example.QuestionSearchService.dto.QuestionDTO;
import com.example.QuestionSearchService.service.QuestionService;
import com.example.QuestionSearchService.service.impl.QuestionServiceImpl;
import org.apache.solr.client.solrj.SolrClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

public class SearchTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    @InjectMocks
    private QuestionController questionController;


    @InjectMocks
    private QuestionServiceImpl questionServiceImpl;



    private MockMvc mockMvc;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(this.questionController).build();

        QuestionDTO question= new QuestionDTO();
        question.setQuestionId("1");
        question.setTitle("Which is the most rated movie in 2017?");
        question.setCategory("Movie");
        List<String> tagList=  new ArrayList<>();
        tagList.add("Bahubali2");
        tagList.add("Bollywood");
        question.setTags(tagList);
        question.setUserName("divya");
        HttpEntity<QuestionDTO> entity = new HttpEntity<QuestionDTO>(question, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/question/save",
                HttpMethod.POST, entity, String.class);


    }
    @After
    public void tearDown()


    {
        // Mockito.verifyNoMoreInteractions(questionService);
    }

    @Test
    public void testAutoSuggest() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/question/autoSuggest/divya",
                HttpMethod.GET, entity, String.class);

        HttpStatus expectedStatus= HttpStatus.OK;

        String expectedRestult="[{\"questionId\":\"1\",\"title\":\"Which is the most rated movie in 2017?\"," +
                "\"category\":\"Movie\",\"tags\":[\"Bahubali2\",\"Bollywood\"]," +
                "\"userName\":\"divya\",\"userId\":\"\"}]";
        Assert.assertEquals(expectedStatus,response.getStatusCode());
        Assert.assertEquals(expectedRestult,response.getBody());


        restTemplate.exchange("http://localhost:8080/question/delete/1",
                HttpMethod.GET, entity, String.class);

        response = restTemplate.exchange("http://localhost:8080/question/autoSuggest/divya",
                HttpMethod.GET, entity, String.class);

        Assert.assertEquals("[]",response.getBody());

    }



}
