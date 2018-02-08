package com.example.QuestionAnswerAPI.QuestionAnswerAPI.service;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.Comment;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.CommentList;

public interface CommentService {
    CommentList findByCommentlistId(String answerId); //    find all comments of a particular answer from comment database
    Integer countCommentsByAnswerId(String answerId); //    count the total comments of a particular answer
    void addComment(Comment comment);   //    to add a comment of a particular answer into comment database
}
