package com.example.QuestionSearchService.service.impl;

import com.example.QuestionSearchService.APIConstants;
import com.example.QuestionSearchService.dto.QuestionDTO;
import com.example.QuestionSearchService.dto.slack;
import com.example.QuestionSearchService.model.Question;
import com.example.QuestionSearchService.service.QuestionService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {


    @Autowired
    private SolrClient client;


    @Override
    public boolean save(QuestionDTO questionDTO) {
        Question question = new Question();
        BeanUtils.copyProperties(questionDTO, question);
        question.setTimeStamp(System.currentTimeMillis());
        SolrInputDocument solrInputDoc = new SolrInputDocument();
        solrInputDoc.addField("id", question.getQuestionId());
        solrInputDoc.addField("title", question.getTitle());
        solrInputDoc.addField("category", question.getCategory());
        solrInputDoc.addField("tags", question.getTags());
        solrInputDoc.addField("timeStamp", System.currentTimeMillis());
        solrInputDoc.addField("userName", question.getUserName());
        solrInputDoc.addField("userId", question.getUserId());
        try {
            client.add(solrInputDoc);
            client.commit();

        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<QuestionDTO> search(String searchTerm) {
        String searchValue = searchTerm + "| *" + searchTerm + "*";
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(searchValue);
        solrQuery.set("defType", "edismax");
        solrQuery.set("qf", "title^1.5 category tags userName");
        solrQuery.set("start",0);
        solrQuery.set("rows",20);

        List<QuestionDTO> searchResultList = new ArrayList<>();
        try {
            QueryResponse response = client.query(solrQuery);

            if (null != response && null != response.getResults() && !response.getResults().isEmpty()) {
                for (SolrDocument doc : response.getResults()) {
                    QuestionDTO questionDTO = new QuestionDTO();
                    questionDTO.setQuestionId((String) doc.get("id"));
                    questionDTO.setTitle(((ArrayList<String>) doc.get("title")).get(0));
                    questionDTO.setCategory(((ArrayList<String>) doc.get("category")).get(0));
                    questionDTO.setTags((ArrayList) doc.get("tags"));
                    if (doc.containsKey("userName")) {
                        questionDTO.setUserName(((ArrayList<String>) doc.get("userName")).get(0));
                    } else {
                        questionDTO.setUserName("");
                    }

                    if (doc.containsKey("userId")) {
                        questionDTO.setUserId(((ArrayList<String>) doc.get("userId")).get(0));
                    } else {
                        questionDTO.setUserId("");
                    }


                    searchResultList.add(questionDTO);
                }

            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return searchResultList;
        }
        return searchResultList;
    }

    @Override
    public void BotSearch(String searchTerm, String response_url) {
        RestTemplate restTemplate = new RestTemplate();

        String searchValue = searchTerm + "| *" + searchTerm + "*";
        List<QuestionDTO> searchResultList = search(searchValue);
        if (searchResultList.size() > 0) {
            List<String> answers = new ArrayList<>();
            String question = "Best Match Question :-\n\n" + searchResultList.get(0).getTitle() + "\n\n";
            try {
                answers = restTemplate.getForObject(APIConstants.QNA_SERVICE_API+"/questionAnswer/coraBotAnswer/" + searchResultList.get(0).getQuestionId(), List.class);

            } catch (Exception e) {
                question += "\nSorry No answer Available";
                restTemplate.postForObject(response_url, new slack(question), String.class);
                // return new ResponseEntity<>(question, HttpStatus.OK);
            }
            String answerBot = "";
            int index = 1;
            for (String answer : answers) {
                if (answer.length() > 0) {
                    answerBot += "Answer \n" + index + " :\n" + answer + "\n\n";
                }
                index++;
            }
            if (answerBot.length() > 0) {
                question += answerBot;
            } else
                question += "\nSorry No answer Available";

            restTemplate.postForObject(response_url, new slack(question), String.class);
            //  return new ResponseEntity<>(question, HttpStatus.OK);


        }
        else
        restTemplate.postForObject(response_url, new slack("Sorry i can't find try with different question..."), String.class);

    }


    @Override
    public int delete(String id) {
        UpdateResponse deleteResponse = new UpdateResponse();
        try {
            deleteResponse = client.deleteById(id);
            client.commit();

        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
        return deleteResponse.getStatus();
    }


}
