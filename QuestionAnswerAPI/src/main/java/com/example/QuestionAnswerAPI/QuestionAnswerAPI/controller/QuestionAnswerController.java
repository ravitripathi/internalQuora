package com.example.QuestionAnswerAPI.QuestionAnswerAPI.controller;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.*;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.*;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.response.CountDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.response.SuccessDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.QuestionAnswerService;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.impl.RestTemplateServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.example.QuestionAnswerAPI.QuestionAnswerAPI.URL.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = QAAPI)
public class QuestionAnswerController {
    @Autowired
    QuestionAnswerService questionAnswerService;

    @Autowired
    RestTemplateServiceImpl restTemplateService;

    /*//  For adding a question without image into database API
    //    input - post method and details of question to add into question database of a particular user
    //    output - question will be added into database and questionId will be returns*/
    @RequestMapping(method = RequestMethod.POST, value = ADDQUESTION)
    public ResponseEntity<?> addQuestion(@RequestBody QuestionDTO questionDTO) {
        System.out.println(questionDTO);
        Question question = new Question();
        BeanUtils.copyProperties(questionDTO, question);
        String queId = UUID.randomUUID().toString().replace("-", "");
        question.setQuestionId(queId);
        String responseUserName = restTemplateService.addQuestionUser(questionDTO);
        question.setImageUrl("");
        question.setUserName(responseUserName);
        Question questionCreated = questionAnswerService.addQuestion(question);
        BeanUtils.copyProperties(question, questionDTO);
        restTemplateService.addQuestionSearch(questionDTO);
        restTemplateService.addQuestionNotification(questionDTO);
        return new ResponseEntity<String>(questionCreated.getQuestionId(), HttpStatus.CREATED);
    }

    /*//  For getting all the questions answered and answers answered count stored in database of a particular user
    //   input : userId
    //   output : countUserDetailDTO will be returned and count of questions of particular user*/
    @RequestMapping(method = RequestMethod.POST, value = COUNTQUESTIONANSWERBYUSERID)
    public ResponseEntity<?> getCountQuestions(@RequestParam("userId") String userId) {
        Integer questionCount = questionAnswerService.countQuestionsbyuserid(userId);
        CountUserDetailsDTO countUserDetailsDTO = new CountUserDetailsDTO();
        countUserDetailsDTO.setUserId(userId);
        countUserDetailsDTO.setQuestionAskedCount(questionCount);
        if(questionAnswerService.questionIdAnsweredByUserId(userId) == null){
            countUserDetailsDTO.setQuestionAnsweredCount(0);
            countUserDetailsDTO.setAnswerCount(0);
            return new ResponseEntity<>(countUserDetailsDTO, HttpStatus.OK);
        }
        countUserDetailsDTO.setQuestionAnsweredCount(questionAnswerService.questionIdAnsweredByUserId(userId).size());
        countUserDetailsDTO.setAnswerCount(questionAnswerService.questionIdAnsweredByUserId(userId).size());
        return new ResponseEntity<>(countUserDetailsDTO, HttpStatus.OK);
    }

    /*//  For getting all the questions stored in database
    //    input - Get method
    //    output - list of DTO of questions*/
    @RequestMapping(method = RequestMethod.GET, value = GETALLQUESTIONS)
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = questionAnswerService.findAllQuestions();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question que : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(que, questionDTO);
            questionDTOList.add(questionDTO);
        }
        return new ResponseEntity<>(questionDTOList, HttpStatus.OK);
    }

    /*//  For getting all the questions of a particular user from the database
    //    input - Get method
    //    output - questionlist contains list of question of user*/
    @RequestMapping(method = RequestMethod.POST, value = GETQUESTIONSBYUSERID)
    public ResponseEntity<?> getQuestionsByUserId(@RequestParam("userId") String userId) {
        List<Question> questions = questionAnswerService.findByUserId(userId);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question que : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(que, questionDTO);
            questionDTOList.add(questionDTO);
        }
        return new ResponseEntity<>(questionDTOList, HttpStatus.OK);
    }

    /*//  For getting all the questions by category from the database
    //    input - Get method
    //    output - questionlist contains list of question of particular category*/
    @RequestMapping(method = RequestMethod.POST, value = GETQUESTIONSBYCATEGORY)
    public ResponseEntity<?> getQuestionsByCategory(@RequestParam("category") String category) {
        List<Question> questions = questionAnswerService.findByCategory(category);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question que : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(que, questionDTO);
            questionDTOList.add(questionDTO);
        }
        return new ResponseEntity<>(questionDTOList, HttpStatus.OK);
    }

    /*//  For getting a question by its questionId stored in database
    //    input - Get method
    //    output - questionDTO of question matches with questionid*/
    @RequestMapping(method = RequestMethod.POST, value = GETQUESTIONBYQUESTIONID)
    public ResponseEntity<?> getQuestionByQuestioId(@RequestParam("questionId") String questionId) {
        Question question = questionAnswerService.findByQuestionId(questionId);
        QuestionDTO questionDTO = new QuestionDTO();
        if(question == null){
            return new ResponseEntity<>(questionDTO, HttpStatus.OK);
        }
        BeanUtils.copyProperties(question, questionDTO);
        RestTemplate restTemplate = new RestTemplate();
        ModeratorDTO categoryModerator = restTemplate.getForObject(ADMINQUESTIONURL+questionDTO.getCategory(), ModeratorDTO.class);
        questionDTO.setCategoryUrl(categoryModerator.getCategoryUrl());
        questionDTO.setModeratorId(categoryModerator.getModeratorId());
        return new ResponseEntity<>(questionDTO, HttpStatus.OK);
    }

    /*//  For deactivating question stored in question database
    //   input : questionId
    //   output : SuccessDTO will be returned*/
    @RequestMapping(method = RequestMethod.GET, value = DEACTIVATEQUESTIONBYQUESTIONID)
    public ResponseEntity<?> deactivateQuestion(@PathVariable("questionId") String questionId) {
        questionAnswerService.deactivateQuestionByQuestionId(questionId);
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(true);
        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    /*//  For getting question answered by user stored in question database
    //   input : userId
    //   output : questionanswer dto and answer by user id*/
    @RequestMapping(method = RequestMethod.POST, value = GETQUESTIONANSWERBYUSERID)
    public ResponseEntity<?> getQuestionAnswerByUserId(@RequestParam("userId") String userId) {
        List<String> questionAnsweredList = questionAnswerService.questionIdAnsweredByUserId(userId);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (String questionId : questionAnsweredList) {
            Question question = questionAnswerService.findByQuestionId(questionId);
            QuestionDTO questionDTO = new QuestionDTO();
            if(question == null){
                return new ResponseEntity<>(questionDTOList, HttpStatus.OK);
            }
            BeanUtils.copyProperties(question, questionDTO);
            questionDTOList.add(questionDTO);
        }
        return new ResponseEntity<>(questionDTOList, HttpStatus.OK);
    }

    /*//  For getting latest questions category wise
    //    input - post method and list of category
    //    output - Question and answers in one dto by contain latest question and answer */
    @RequestMapping(method = RequestMethod.POST, value = GETLATESTQUESTIONBYCATEGORY)
    public ResponseEntity<?> getLatestByCategory(@RequestBody List<String> category) { //category of postdto will be same as answerId
        System.out.println(category);
        Map<String, List<Question>> questionCategoryMap = new HashMap<String, List<Question>>();
        questionCategoryMap = questionAnswerService.getLatestByCategory(category);
        return new ResponseEntity(questionCategoryMap, HttpStatus.CREATED);
    }

    /*//  For getting count of question by category
    //    input - get method and category
    //    output - countDTO of question */
    @RequestMapping(method = RequestMethod.GET, value = COUNTQUESTIONBYCATEGORY)
    public ResponseEntity<?> getCountQuestionByCategory(@PathVariable("category") String category) { //category of postdto will be same as answerId
        System.out.println(category);
        Integer countQuestionsByCategory = questionAnswerService.countQuestionsByCategory(category);
        return new ResponseEntity(countQuestionsByCategory, HttpStatus.CREATED);
    }

    /*//  For getting a question title by its questionId stored in database
    //    input - Get method
    //    output - questio title  of question matches with questionid*/
    @RequestMapping(method = RequestMethod.GET, value = GETQUESTIONTITLEBYQUESTIONID)
    public ResponseEntity<?> getQuestionTitleByQuestioId(@PathVariable("questionId") String questionId) {
        Question question = questionAnswerService.findByQuestionId(questionId);
        if(question == null){
            return new ResponseEntity<>("No Question Found!!!", HttpStatus.OK);
        }
        String questionTitle = question.getTitle();
        return new ResponseEntity<>(questionTitle, HttpStatus.OK);
    }

//    private static final String STORAGE_PATH_QUESTION = "/Users/coviam/Desktop/QuestionAnswerAPI/photos/question";
//    private static final String STORAGE_PATH_ANSWER = "/Users/coviam/Desktop/QuestionAnswerAPI/photos/answer";

//    /*//  For adding an answer without image into database API
//    //    input - post method and details of answer to add into answer without image database of a particular question
//    //    output - answer will be added into database and status will be returns*/
//    @RequestMapping(method = RequestMethod.POST, value = ADDANSWER)
//    public ResponseEntity<?> addAnswer(@RequestParam("questionId") String questionId, @RequestParam("userId") String userId, @RequestParam("answer") String answer) { //answerid of postdto will be same as questionid
//        AnswerDTO answerDTO = new AnswerDTO();
//        answerDTO.setQuestionId(questionId);
//        answerDTO.setAnswer(answer);
//        answerDTO.setUserId(userId);
//        answerDTO.setImageUrl("");
//        String ansId = UUID.randomUUID().toString().replace("-", "");
//        Answer answerData = new Answer();
//        BeanUtils.copyProperties(answerDTO, answerData);
//        answerData.setId(ansId);
//        String responseUserName = restTemplateService.addAnswerUser(answerDTO);
//        answerData.setUserName(responseUserName);
//        questionAnswerService.addAnswer(answerData);
//        questionAnswerService.addQuestionAnswer(answerData);
//        restTemplateService.addAnswerNotification(answerDTO);
//        return new ResponseEntity<String>(answerData.getId(), HttpStatus.CREATED);
//    }

//    /*//  For getting all the answer of a particular question stored in database
//    //    input - Get method
//    //    output - answerlist contains list of answers of questions*/
//    @RequestMapping(method = RequestMethod.GET, value = GETANSWERSBYQUESTIONID)
//    public ResponseEntity<?> getAnswersByQuestionId(@PathVariable("questionId") String questionId) {
//        AnswerList answerList = questionAnswerService.findByAnswerlistId(questionId);
//        AnswerListDTO answerListDTO = new AnswerListDTO();
//        if(answerList == null){
//            return new ResponseEntity<>(answerListDTO, HttpStatus.OK);
//        }
//        BeanUtils.copyProperties(answerList, answerListDTO);
//        return new ResponseEntity<>(answerListDTO, HttpStatus.OK);
//    }

//    /*//  For getting all the comments of an answer stored in database
//    //    input - Get method
//    //    output - commentlist contains list of comments of answer*/
//    @RequestMapping(method = RequestMethod.GET, value = GETCOMMENTSBYANSWERID)
//    public ResponseEntity<?> getCommentsByAnswerId(@PathVariable("answerId") String answerId) {
//        CommentList commentList = questionAnswerService.findByCommentlistId(answerId);
//        CommentListDTO commentListDTO = new CommentListDTO();
//        if(commentList == null){
//            return new ResponseEntity<>(commentListDTO, HttpStatus.OK);
//        }
//        BeanUtils.copyProperties(commentList, commentListDTO);
//        List<CommentDTO> listOfComment = new ArrayList<>();
//        List<CommentDTO> listOfCommentDTO = new ArrayList<>();
//        listOfComment = commentListDTO.getCommentDTOList();
//        for(CommentDTO commentDTO : listOfComment){
//            String responseUserName = restTemplateService.addCommentUser(commentDTO);
//            commentDTO.setUserName(responseUserName);
//            listOfCommentDTO.add(commentDTO);
//        }
//        commentListDTO.setCommentDTOList(listOfCommentDTO);
//        return new ResponseEntity<>(commentListDTO, HttpStatus.OK);
//    }
//
//    /*//  For getting count of the answers stored in database of a particular questions
//    //   input : questionId
//    //   output : countDTO will be returned and count of answer of particular question*/
//    @RequestMapping(method = RequestMethod.GET, value = COUNTANSWERBYQUESTIONID)
//    public ResponseEntity<?> getCountAnswersByQuestionId(@PathVariable("questionId") String questionId) {
//        Integer answersCount = questionAnswerService.countAnswersByQuestionId(questionId);
//        CountDTO countDTO = new CountDTO();
//        countDTO.setCountByUserId(answersCount);
//        return new ResponseEntity<>(countDTO, HttpStatus.OK);
//    }

//    /*//  For getting count of the comments stored in database of a particular answer
//    //   input : userId
//    //   output : countDTO will be returned and count of questions of particular user*/
//    @RequestMapping(method = RequestMethod.GET, value = COUNTCOMMENTSBYANSWERID)
//    public ResponseEntity<?> getCountCommentsByAnswerId(@PathVariable("answerId") String answerId) {
//        Integer commentCount = questionAnswerService.countCommentsByAnswerId(answerId);
//        CountDTO countDTO = new CountDTO();
//        countDTO.setCountByUserId(commentCount);
//        return new ResponseEntity<>(countDTO, HttpStatus.OK);
//    }

//    /*//  For getting count of the upvotes stored in database of a particular answer
//    //   input : userId
//    //   output : countDTO will be returned and count of questions of particular user*/
//    @RequestMapping(method = RequestMethod.GET, value = COUNTUPVOTESBYANSWERID)
//    public ResponseEntity<?> getCountUpvotesByAnswerId(@PathVariable("answerId") String answerId) {
//        Integer commentCount = questionAnswerService.countUpvotesByAnswerId(answerId);
//        CountDTO countDTO = new CountDTO();
//        countDTO.setCountByUserId(commentCount);
//        return new ResponseEntity<>(countDTO, HttpStatus.OK);
//    }

//    /*//  For adding a comment int database API
//        //    input - post method and details of comment to add into comment database of a particular answer
//        //    output - comment will be added into database and status will be returned*/
//    @RequestMapping(method = RequestMethod.POST, value = ADDCOMMENT)
//    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO) { //commentId of postdto will be same as answerId
//        System.out.println(commentDTO);
//        Comment comment = new Comment();
//        BeanUtils.copyProperties(commentDTO, comment);
//        String cmtId = UUID.randomUUID().toString().replace("-", "");
//        comment.setCommentId(cmtId);
//        questionAnswerService.addComment(comment);
//        SuccessDTO successDTO = new SuccessDTO();
//        successDTO.setStatus(true);
//        return new ResponseEntity<SuccessDTO>(successDTO, HttpStatus.CREATED);
//    }
//
//    /*//  For adding an upvote int database API
//    //    input - post method and details of upvote(answerId, userId) to add into upvote database of a particular answer
//    //    output - upvote will be added into database and status will be returns*/
//    @RequestMapping(method = RequestMethod.POST, value = ADDUPVOTE)
//    public ResponseEntity<?> addUpvote(@RequestBody UpvoteDTO upvoteDTO) { //upvoteId of postdto will be same as answerId
//        System.out.println(upvoteDTO);
//        int flag = questionAnswerService.addUpvote(upvoteDTO);
//        if (flag == 1) {
//            return new ResponseEntity<>("Upvote Removed", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Upvote Added", HttpStatus.CREATED);
//    }
//
//    /*//  For getting answers
//    //    input - get method and questionId
//    //    output - list of string contains answers */
//    @RequestMapping(method = RequestMethod.GET, value = CORABOTANSWER)
//    public ResponseEntity<?> getAnswersForBot(@PathVariable("questionId") String questionId) {
//        List<String> listOfString = questionAnswerService.getLatestAnswer(questionId);
//        if(listOfString == null){
//            listOfString = new ArrayList<>();
//            return new ResponseEntity<>(listOfString, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(listOfString, HttpStatus.OK);
//    }
}
