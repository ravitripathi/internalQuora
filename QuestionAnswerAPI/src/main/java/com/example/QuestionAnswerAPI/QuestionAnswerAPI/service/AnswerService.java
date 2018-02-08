package com.example.QuestionAnswerAPI.QuestionAnswerAPI.service;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.AnswerDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.AnswerListDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.Answer;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.AnswerList;

import java.util.List;

public interface AnswerService {

    AnswerList findByAnswerlistId(String answerListId); //    find all answers of a particular question from answer database
    List<AnswerList> findAllAnsList();  //    find all answers added in database
    Integer countAnswersByQuestionId(String questionId);   //    count the total answers of a particular question
    void addAnswer(Answer answer);  //    to add an answer of a particular question into answer database
    List<String> getLatestAnswer(String anserListId);   //get latest three answer from answerlist
    void addQuestionAnswer(Answer answer);  //add answer into questionAnswer database
    List<AnswerDTO> getAnswerSortedByUpvotes(AnswerListDTO answerListDTO);  //get answer by sorted by upvote
}
