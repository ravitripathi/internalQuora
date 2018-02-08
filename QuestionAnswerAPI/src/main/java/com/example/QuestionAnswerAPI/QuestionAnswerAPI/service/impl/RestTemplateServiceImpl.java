package com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.impl;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.AnswerDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.CommentDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.QuestionDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.slack;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.Question;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.QuestionAnswerAPI.QuestionAnswerAPI.URL.*;

@RestController
public class RestTemplateServiceImpl {
//    for getting username from userid when question is added
    public String addQuestionUser(QuestionDTO questionDTO){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("userId", questionDTO.getUserId());
        RestTemplate restTemplate = new RestTemplate();
        String responseUserName = restTemplate.postForObject(USERURL ,body, String.class);
        return responseUserName;
    }

//    for sending question to add into search database
    public void addQuestionSearch(QuestionDTO questionDTO){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(SOLRSEARCH, questionDTO, String.class);
    }

//    for notifying notificatio url when question is added
    public void addQuestionNotification(QuestionDTO questionDTO){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String queId = questionDTO.getQuestionId();
        RestTemplate restTemplate = new RestTemplate();
        executorService.execute(new Runnable() {
            public void run() {
                MultiValueMap<String, String> bodynotify = new LinkedMultiValueMap<String, String>();
                bodynotify.add("sender", questionDTO.getUserId());
                bodynotify.add("category", questionDTO.getCategory());
                bodynotify.add("question_id", queId);
                restTemplate.postForObject(NOTIFICATIONURLNEWPOSTQUESTION, bodynotify, String.class);

                restTemplate.postForObject(CORABOT, new slack(questionDTO.getUserName()+" just asked a question: '"+questionDTO.getTitle() + "' in the category "+questionDTO.getCategory()+"\nhttp://localhost:8080/home/feed/"+queId), String.class);
            }
        });
        executorService.shutdown();
    }

//    for getting username from userid when question is added
    public String addAnswerUser(AnswerDTO answerDTO){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("userId", answerDTO.getUserId());
        RestTemplate restTemplate = new RestTemplate();
        String responseUserName = restTemplate.postForObject(USERURL ,body, String.class);
        return responseUserName;
    }


    //    for notifying notificatio url when answer is added
    public void addAnswerNotification(AnswerDTO answerDTO){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String ansId = answerDTO.getId();
        RestTemplate restTemplate = new RestTemplate();
        executorService.execute(new Runnable() {
            public void run() {
                MultiValueMap<String, String> bodynotify = new LinkedMultiValueMap<String, String>();
                bodynotify.add("user_id", answerDTO.getUserId());
                bodynotify.add("answer_id", ansId);
                bodynotify.add("question_id", answerDTO.getQuestionId());
                restTemplate.postForObject(NOTIFICATIONURLNEWPOSTANSWER, bodynotify, String.class);
            }
        });
        executorService.shutdown();
    }

    //    for getting username from userid when question is added
    public String addCommentUser(CommentDTO commentDTO){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("userId", commentDTO.getUserId());
        RestTemplate restTemplate = new RestTemplate();
        String responseUserName = restTemplate.postForObject(USERURL ,body, String.class);
        return responseUserName;
    }
}
