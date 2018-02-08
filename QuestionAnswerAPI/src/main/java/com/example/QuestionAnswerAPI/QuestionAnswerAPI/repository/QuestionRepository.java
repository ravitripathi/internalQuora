package com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String>{
    public List<Question> getByUserId(String userId);
    public List<Question> getByCategory(String category);
    public Integer countByUserId(String userId);
    public List<Question> getByCategoryOrderByTimestampDesc(String category);
    public Integer countByCategory(String category);
}
