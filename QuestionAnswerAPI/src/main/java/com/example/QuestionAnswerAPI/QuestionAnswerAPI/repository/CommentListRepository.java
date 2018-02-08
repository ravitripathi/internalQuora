package com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.CommentList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentListRepository extends MongoRepository<CommentList, String> {
    public CommentList getByCommentListId(String CommentListId);
}
