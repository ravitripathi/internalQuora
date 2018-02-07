package com.example.QuestionSearchService.service;

import com.example.QuestionSearchService.dto.QuestionDTO;
import com.example.QuestionSearchService.model.Question;

import java.util.List;

public interface QuestionService {

    public boolean save(QuestionDTO questionDTO);

    public int delete(String id);

    public List<QuestionDTO> search(String searchTerm);

public void BotSearch(String searchTerm,String response_url);




}
