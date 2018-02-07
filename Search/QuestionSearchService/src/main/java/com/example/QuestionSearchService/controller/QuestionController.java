package com.example.QuestionSearchService.controller;


import com.example.QuestionSearchService.APIConstants;
import com.example.QuestionSearchService.dto.QuestionDTO;
import com.example.QuestionSearchService.dto.slack;
import com.example.QuestionSearchService.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<String> addOrUpdateQuestion(@RequestBody QuestionDTO questionDTO) {

        boolean isQuestionCreated = questionService.save(questionDTO);
        if (isQuestionCreated) {
            return new ResponseEntity<String>("Success", HttpStatus.OK);

        }
        return new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);

    }


    @RequestMapping(method = RequestMethod.GET, path = "/autoSuggest/{userQuery}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity autoSuggest(@PathVariable("userQuery") String searchTerm) {
        List<QuestionDTO> searchResultList = questionService.search(searchTerm);
        return new ResponseEntity<>(searchResultList, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteQuestion(@PathVariable("id") String id) {
        int deleteResponseStatus = questionService.delete(id);
        if (deleteResponseStatus != 0) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);

    }

/*
    @RequestMapping(method = RequestMethod.GET, path = "/botSearch/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity slackBotSearch(@PathVariable("query") String searchTerm) {
        String searchValue = searchTerm + "| *" + searchTerm + "*";
        List<QuestionDTO> searchResultList = questionService.search(searchValue);
        if (searchResultList.size() > 0) {
            RestTemplate restTemplate = new RestTemplate();
            List<String> answers = new ArrayList<>();
            String question = "Best Match Question :-\n\n" + searchResultList.get(0).getTitle() + "\n\n";
            try {
                answers = restTemplate.getForObject("http://10.177.7.117:8080/questionAnswer/coraBotAnswer/" + searchResultList.get(0).getQuestionId(), List.class);

            } catch (Exception e) {
                question += "\nSorry No answer Available";
                return new ResponseEntity<>(question, HttpStatus.OK);
            }
            String answerBot = "";
            int index = 1;
            for (String answer : answers) {
                if (answer.length() > 0) {
                    answerBot += "Answer \n" + index + " :\n" + answer + "\n\n";
                }
                index++;
            }
            if (answerBot.length() > 0) {
                question += answerBot;
            } else
                question += "\nSorry No answer Available";
            return new ResponseEntity<>(question, HttpStatus.OK);


        }
        return new ResponseEntity<>("Sorry i can't find try with different question...", HttpStatus.NOT_FOUND);
    }

    */
    //@RequestMapping(method = RequestMethod.POST, path = "/botSearchPost/{text}/{response_url}",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    //  produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    //@ResponseBody

    @RequestMapping(value = "/botSearchPost", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void slackBotSearchPost(@RequestParam Map<String, String> map) {
        String response_url = map.get("response_url");
        String searchTerm = map.get("text");
        //System.out.println(response_url);

        questionService.BotSearch(searchTerm,response_url);

        // RestTemplate restTemplate=new RestTemplate();
//        RestTemplate restTemplate = new RestTemplate();
//
//        String searchValue = searchTerm + "| *" + searchTerm + "*";
//        List<QuestionDTO> searchResultList = questionService.search(searchValue);
//        if (searchResultList.size() > 0) {
//            List<String> answers = new ArrayList<>();
//            String question = "Best Match Question :-\n\n" + searchResultList.get(0).getTitle() + "\n\n";
//            try {
//                answers = restTemplate.getForObject(APIConstants.QNA_SERVICE_API+"/questionAnswer/coraBotAnswer/" + searchResultList.get(0).getQuestionId(), List.class);
//
//            } catch (Exception e) {
//                question += "\nSorry No answer Available";
//                restTemplate.postForObject(response_url, new slack(question), String.class);
//                // return new ResponseEntity<>(question, HttpStatus.OK);
//            }
//            String answerBot = "";
//            int index = 1;
//            for (String answer : answers) {
//                if (answer.length() > 0) {
//                    answerBot += "Answer \n" + index + " :\n" + answer + "\n\n";
//                }
//                index++;
//            }
//            if (answerBot.length() > 0) {
//                question += answerBot;
//            } else
//                question += "\nSorry No answer Available";
//
//            restTemplate.postForObject(response_url, new slack(question), String.class);
//            //  return new ResponseEntity<>(question, HttpStatus.OK);
//
//
//        }
//        restTemplate.postForObject(response_url, new slack("Sorry i can't find try with different question..."), String.class);

        //return new ResponseEntity<>("Sorry i can't find try with different question...", HttpStatus.OK);


    }

}