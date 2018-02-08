package com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.UpvoteList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpvoteListRepository extends MongoRepository<UpvoteList, String> {
    public UpvoteList getByUpvoteListId(String UpvoteListId);
}