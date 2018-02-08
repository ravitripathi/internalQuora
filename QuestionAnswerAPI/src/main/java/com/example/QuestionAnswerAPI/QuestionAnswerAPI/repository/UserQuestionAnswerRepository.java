package com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.Answer;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.UserQuestionAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserQuestionAnswerRepository extends MongoRepository<UserQuestionAnswer, String>{
    public UserQuestionAnswer getByUserId(String userId);
}
