package com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.impl;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.*;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.*;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository.*;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.QuestionAnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserQuestionAnswerRepository userQuestionAnswerRepository;

//    get all questions of a particular user from question database
    @Override
    public List<Question> findByUserId(String userId) {
        return questionRepository.getByUserId(userId);
    }

//    get all questions of a particular category from question database
    @Override
    public List<Question> findByCategory(String category) {
        return questionRepository.getByCategory(category);
    }

//    find all questions added in database
    @Override
    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

//    find question by questioId
    @Override
    public Question findByQuestionId(String questionId) {
        return questionRepository.findOne(questionId);
    }


//    count the total questions of a particular user
    @Override
    public Integer countQuestionsbyuserid(String userId) {
        return questionRepository.countByUserId(userId);
    }

    @Override
    public Integer countQuestionsByCategory(String category) {
        return questionRepository.countByCategory(category);
    }

    //    to add a question of a particular user into question database
    @Override
    public Question addQuestion(Question question) {
        question.setTimestamp(Date.from(Instant.now()).toString());

        return questionRepository.save(question);
    }

//    to deactivate question by questionId
    @Override
    public void deactivateQuestionByQuestionId(String questionId) {
        Question question = questionRepository.findOne(questionId);
        if(question.isActive()) {
            question.setActive(false);
        }
        else{
            question.setActive(true);
        }
        questionRepository.save(question);
        return;
    }

//    to return latest question added by category
   @Override
    public Map<String, List<Question>> getLatestByCategory(List<String> category) {
        Map<String, List<Question>> questionCategoryMap = new HashMap<String, List<Question>>();
        for(String cate : category){
            List<Question> questionList = questionRepository.getByCategoryOrderByTimestampDesc(cate);

            List<Question> questions = new ArrayList<>();
            for(int i=0; i<questionList.size() && i<2; i++){
                questions.add(questionList.get(i));
            }
            questionCategoryMap.put(cate, questions);
       }
        return questionCategoryMap;
    }

//find questions answered by particular user
    @Override
    public List<String> questionIdAnsweredByUserId(String userId) {
        UserQuestionAnswer userQuestionAnswer = new UserQuestionAnswer();
        if(userQuestionAnswerRepository.findOne(userId) != null){
            userQuestionAnswer = userQuestionAnswerRepository.findOne(userId);
            return userQuestionAnswer.getQuestionIdList();
        }else{
            return null;
        }
    }

//    to add an answer of a particular question into user questionanswer database along with questionId
    @Override
    public void addQuestionAnswer(Answer answer) {
        UserQuestionAnswer userQuestionAnswer;
        if(userQuestionAnswerRepository.findOne(answer.getUserId()) != null){
            userQuestionAnswer = userQuestionAnswerRepository.findOne(answer.getUserId());
        }
        else{
            userQuestionAnswer = new UserQuestionAnswer();
        }
        List<String> questionIdList;
        List<String> answerIdList;
        if(userQuestionAnswer.getQuestionIdList() == null){
            questionIdList = new ArrayList<>();
        }
        else{
            questionIdList = userQuestionAnswer.getQuestionIdList();
        }

        if(!questionIdList.contains(answer.getQuestionId())){
            questionIdList.add(answer.getQuestionId());
        }

        if(userQuestionAnswer.getAnswerIdList() == null){
            answerIdList = new ArrayList<>();
        }
        else{
            answerIdList = userQuestionAnswer.getAnswerIdList();
        }
        userQuestionAnswer.setUserId(answer.getUserId());
        answerIdList.add(answer.getId());
        userQuestionAnswer.setQuestionIdList(questionIdList);
        userQuestionAnswer.setAnswerIdList(answerIdList);
        userQuestionAnswerRepository.save(userQuestionAnswer);
    }

//
//    //find
//    @Override
//    public List<String> answerIdAnsweredByUserId(String userId) {
//        UserQuestionAnswer userQuestionAnswer = userQuestionAnswerRepository.findOne(userId);
//        return userQuestionAnswer.getAnswerIdList();
//    }



////    find all comments by answerId
//    @Override
//    public CommentList findByCommentlistId(String answerId) {
//        return commentListRepository.findOne(answerId);
//    }

////    count the total comments of a particular answer
//    @Override
//    public Integer countCommentsByAnswerId(String answerId) {
//        return (commentListRepository.getByCommentListId(answerId).getCommentDTOList().size());
//    }

////    count the total upvotes of a particular answer
//    @Override
//    public Integer countUpvotesByAnswerId(String answerId) {
//        return (upvoteListRepository.getByUpvoteListId(answerId).getUserIdList().size());
//    }

//    @Override
//    public void addComment(Comment comment){
//        comment.setTimestamp(Date.from(Instant.now()).toString());
//        CommentDTO commentDTO  = new CommentDTO();
//        BeanUtils.copyProperties(comment, commentDTO);
//        System.out.println(commentDTO);
//        CommentListDTO commentListDTO;
//        CommentList commentList;
//        commentList = commentListRepository.getByCommentListId((commentDTO.getAnswerId()));
//        if( commentList == null || commentList.getCommentDTOList() == null) {
//            commentList= new CommentList();
//            commentListDTO = new CommentListDTO();
//            List<CommentDTO> commentDTOList = new ArrayList<>();
//            commentDTOList.add(commentDTO);
//            commentListDTO.setCommentDTOList(commentDTOList);
//            commentListDTO.setCommentListId(comment.getCommentId());
//            BeanUtils.copyProperties(commentListDTO, commentList);
//        }
//        else{
//            commentList = commentListRepository.getByCommentListId(commentDTO.getAnswerId());
//            commentListDTO = new CommentListDTO();
//            BeanUtils.copyProperties(commentList, commentListDTO);
//            commentListDTO.getCommentDTOList().add(commentDTO);
//            //commentDTO.setCommentId(String.valueOf(commentListDTO.getCommentDTOList().size()));
//            BeanUtils.copyProperties(commentListDTO, commentList);
//        }
//        //commentList.setCommentListId(comment.getCommentId());
//        commentList.setCommentListId(comment.getAnswerId());
//        commentListRepository.save(commentList);
//        return;
//    }

////    to add an upvote of a particular answer into upvote database
//    @Override
//    public int addUpvote(UpvoteDTO upvoteDTO){
//        UpvoteList upvoteList = upvoteListRepository.findOne(upvoteDTO.getUpvoteId());
//        if(upvoteList == null){
//            upvoteList = new UpvoteList();
//            upvoteList.setUpvoteListId(upvoteDTO.getUpvoteId());
//            List<String> userIdList = new ArrayList<>();
//            userIdList.add(upvoteDTO.getUserId());
//            upvoteList.setUserIdList(userIdList);
//            upvoteListRepository.save(upvoteList);
//            return 0;
//        }
//        for(String userId : upvoteList.getUserIdList()){
//            if(upvoteDTO.getUserId().equals(userId)){
//                List<String> userIdList;
//                userIdList = upvoteList.getUserIdList();
//                userIdList.remove(userId);
//                upvoteList.setUserIdList(userIdList);
//                upvoteListRepository.save(upvoteList);
//                return 1;
//            }
//        }
//        List<String> userIdList;
//        upvoteList.getUserIdList().add(upvoteDTO.getUserId());
//        userIdList = upvoteList.getUserIdList();
//        upvoteList.setUserIdList(userIdList);
//        upvoteListRepository.save(upvoteList);
//        return 0;
//    }


}
