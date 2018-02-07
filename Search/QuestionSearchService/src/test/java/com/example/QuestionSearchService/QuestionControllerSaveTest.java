package com.example.QuestionSearchService;

import com.example.QuestionSearchService.controller.QuestionController;
import com.example.QuestionSearchService.dto.QuestionDTO;
import com.example.QuestionSearchService.model.Question;
import com.example.QuestionSearchService.service.QuestionService;
import com.example.QuestionSearchService.service.impl.QuestionServiceImpl;
import org.apache.solr.client.solrj.SolrClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionControllerSaveTest {

    private MockMvc mockMvc;

    @InjectMocks
    QuestionController questionController;

    @Mock
    private QuestionService questionService;




    @InjectMocks
    QuestionServiceImpl questionServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.questionController).build();
    }


    @Test
    public void should_SaveQuestion_When_ValidRequest() throws Exception {

        when(questionService.save(any(QuestionDTO.class))).thenReturn(true);

        mockMvc.perform(post("/question/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"questionId\": \"1\", \"title\": \"Which is the most rated movie in 2017?\",\"category\":\"Movie\"," +
                        "\"tags\": [\"Bahubali2\",\"Bollywood\"], \"userName\":\"Divya\",\"userId\":\"divya@gmail.com\"}")
        ).andExpect(status().isOk())
                .andExpect(content().string("Success"));



    }




}
