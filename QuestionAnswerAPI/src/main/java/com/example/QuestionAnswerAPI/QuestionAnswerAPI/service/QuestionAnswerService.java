package com.example.QuestionAnswerAPI.QuestionAnswerAPI.service;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.QuestionAnswerDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.UpvoteDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.*;

import java.util.List;
import java.util.Map;

public interface QuestionAnswerService {
    List<Question> findByUserId(String userId); //    get all questions of a particular user from question database
    List<Question> findByCategory(String category); //find all questions by category
    List<Question> findAllQuestions();  //    find all questions added in database
    Question findByQuestionId(String questionId);   //find question bny questionId
    //CommentList findByCommentlistId(String answerId); //    find all comments of a particular answer from comment database
    Integer countQuestionsbyuserid(String userId);  //    count the total questions of a particular user
    Integer countQuestionsByCategory(String category);  //    count the total questions of a particular category
    //Integer countCommentsByAnswerId(String answerId); //    count the total comments of a particular answer
    //Integer countUpvotesByAnswerId(String answerId);  //    count the total upvotes of a particular answer
    Question addQuestion(Question question);    //    to add a question of a particular user into question database
    //void addComment(Comment comment);   //    to add a comment of a particular answer into comment database
    //int addUpvote(UpvoteDTO upvoteDTO); //    to add an upvote of a particular answer into upvote database
                                        //    0-not in db : 1-already upvoted
    void deactivateQuestionByQuestionId(String questionId);  //deactivate question by questionId
    Map<String, List<Question>> getLatestByCategory(List<String> category);  //find latest 2-2 question sorted by time categorywise
    List<String> questionIdAnsweredByUserId(String userId); //list of questionId answered by user
    //List<String> answerIdAnsweredByUserId(String userId); //list of answerId answered by user
    void addQuestionAnswer(Answer answer);  //add answer into questionAnswer database


}
