package com.example.QuestionAnswerAPI.QuestionAnswerAPI.controller;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.AnswerDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.AnswerListDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.Answer;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.AnswerList;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.response.CountDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.AnswerService;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.QuestionAnswerService;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.impl.RestTemplateServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.example.QuestionAnswerAPI.QuestionAnswerAPI.URL.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@CrossOrigin("*")
@RestController
@RequestMapping(value = QAAPI)
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @Autowired
    RestTemplateServiceImpl restTemplateService;

    /*//  For adding an answer without image into database API
    //    input - post method and details of answer to add into answer without image database of a particular question
    //    output - answer will be added into database and status will be returns*/
    @RequestMapping(method = RequestMethod.POST, value = ADDANSWER)
    public ResponseEntity<?> addAnswer(@RequestParam("questionId") String questionId, @RequestParam("userId") String userId, @RequestParam("answer") String answer) { //answerid of postdto will be same as questionid
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setQuestionId(questionId);
        answerDTO.setAnswer(answer);
        answerDTO.setUserId(userId);
        answerDTO.setImageUrl("");
        String ansId = UUID.randomUUID().toString().replace("-", "");
        Answer answerData = new Answer();
        BeanUtils.copyProperties(answerDTO, answerData);
        answerData.setId(ansId);
        String responseUserName = restTemplateService.addAnswerUser(answerDTO);
        answerData.setUserName(responseUserName);
        answerService.addAnswer(answerData);
        answerService.addQuestionAnswer(answerData);
        restTemplateService.addAnswerNotification(answerDTO);
        return new ResponseEntity<String>(answerData.getId(), HttpStatus.CREATED);
    }

    /*//  For getting all the answer of a particular question stored in database
    //    input - Get method
    //    output - answerlist contains list of answers of questions*/
    @RequestMapping(method = RequestMethod.GET, value = GETANSWERSBYQUESTIONID)
    public ResponseEntity<?> getAnswersByQuestionId(@PathVariable("questionId") String questionId) {
        AnswerList answerList = answerService.findByAnswerlistId(questionId);
        AnswerListDTO answerListDTO = new AnswerListDTO();
        if(answerList == null){
            return new ResponseEntity<>(answerListDTO, HttpStatus.OK);
        }
        BeanUtils.copyProperties(answerList, answerListDTO);
        return new ResponseEntity<>(answerListDTO, HttpStatus.OK);
    }

    /*//  For getting count of the answers stored in database of a particular questions
    //   input : questionId
    //   output : countDTO will be returned and count of answer of particular question*/
    @RequestMapping(method = RequestMethod.GET, value = COUNTANSWERBYQUESTIONID)
    public ResponseEntity<?> getCountAnswersByQuestionId(@PathVariable("questionId") String questionId) {
        Integer answersCount = answerService.countAnswersByQuestionId(questionId);
        CountDTO countDTO = new CountDTO();
        countDTO.setCountByUserId(answersCount);
        return new ResponseEntity<>(countDTO, HttpStatus.OK);
    }

    /*//  For getting answers
    //    input - get method and questionId
    //    output - list of string contains answers */
    @RequestMapping(method = RequestMethod.GET, value = CORABOTANSWER)
    public ResponseEntity<?> getAnswersForBot(@PathVariable("questionId") String questionId) {
        List<String> listOfString = answerService.getLatestAnswer(questionId);
        if(listOfString == null){
            listOfString = new ArrayList<>();
            return new ResponseEntity<>(listOfString, HttpStatus.OK);
        }
        return new ResponseEntity<>(listOfString, HttpStatus.OK);
    }

    /*//  For getting all the answer of a particular question stored in database sorted by upvotes
    //    input - Get method
    //    output - answerlist contains list of answers of questions*/
    @RequestMapping(method = RequestMethod.GET, value = GETANSWERBYUPVOTESORTED)
    public ResponseEntity<?> getAnswersByQuestionIdSortedByUpvotes(@PathVariable("questionId") String questionId) {
        AnswerList answerList = answerService.findByAnswerlistId(questionId);
        AnswerListDTO answerListDTO = new AnswerListDTO();
        answerListDTO.setAnswerDTOList(new ArrayList<AnswerDTO>());
        if(answerList == null){
            return new ResponseEntity<>(answerListDTO, HttpStatus.OK);
        }
        answerListDTO.setAnswerListId(answerListDTO.getAnswerListId());
        for (Answer answer:answerList.getAnswerDTOList()){
            AnswerDTO answerDTO=new AnswerDTO();
            BeanUtils.copyProperties( answer, answerDTO);
            answerListDTO.getAnswerDTOList().add(answerDTO);
        }
        //BeanUtils.copyProperties(answerList, answerListDTO);
        List<AnswerDTO> answerDTOList = answerService.getAnswerSortedByUpvotes(answerListDTO);
        answerListDTO.setAnswerDTOList(answerDTOList);
        return new ResponseEntity<>(answerListDTO, HttpStatus.OK);
    }
}