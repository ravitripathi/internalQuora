package com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.impl;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.AnswerDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.AnswerListDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.Answer;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.AnswerList;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.UpvoteList;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.UserQuestionAnswer;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository.AnswerListRepository;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository.UpvoteListRepository;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository.UserQuestionAnswerRepository;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.AnswerService;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.QuestionAnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerListRepository answerListRepository;

    @Autowired
    UserQuestionAnswerRepository userQuestionAnswerRepository;

    @Autowired
    UpvoteListRepository upvoteListRepository;

//    find all answers of a particular question from answer database
    @Override
    public AnswerList findByAnswerlistId(String answerListId) {
        return answerListRepository.getByAnswerListId(answerListId);
    }

//    find all answers added in database
    @Override
    public List<AnswerList> findAllAnsList() {
        return answerListRepository.findAll();
    }

//    count the total answers of a particular question
    @Override
    public Integer countAnswersByQuestionId(String questionId) {
        if(answerListRepository.getByAnswerListId(questionId) == null){
            return 0;
        }
        return (answerListRepository.getByAnswerListId(questionId).getAnswerDTOList().size());
    }

//    to add a comment of a particular answer into comment database
    @Override
    public void addAnswer(Answer answer) {
        answer.setTimestamp(Date.from(Instant.now()).toString());
        AnswerDTO answerDTO  = new AnswerDTO();
        BeanUtils.copyProperties(answer, answerDTO);
        System.out.println(answerDTO);
        AnswerListDTO answerListDTO;
        AnswerList answerList;
        answerList=answerListRepository.getByAnswerListId(answerDTO.getQuestionId());
        if( answerList==null || answerList.getAnswerDTOList()==null) {
            answerList= new AnswerList();
            answerListDTO = new AnswerListDTO();
            List<AnswerDTO> answerDTOList = new ArrayList<>();
            answerDTOList.add(answerDTO);
            // answerDTO.setAnswerId("1");
            answerListDTO.setAnswerDTOList(answerDTOList);
            answerListDTO.setAnswerListId(answer.getId());
            BeanUtils.copyProperties(answerListDTO, answerList);
        }
        else{
            answerList = answerListRepository.getByAnswerListId(answerDTO.getQuestionId());
            answerListDTO =new AnswerListDTO();
            BeanUtils.copyProperties(answerList, answerListDTO);
            answerListDTO.getAnswerDTOList().add(answerDTO);
            //answerDTO.setAnswerId(String.valueOf(answerListDTO.getAnswerDTOList().size()));
            BeanUtils.copyProperties(answerListDTO, answerList);
        }
        answerList.setAnswerListId(answer.getQuestionId());
        answerListRepository.save(answerList);
        return;
    }

//    for getting latest answer for Bot
    @Override
    public List<String> getLatestAnswer(String anserListId) {
        List<String> listOfAnswer = new ArrayList<>();
        AnswerList answerList = answerListRepository.findOne(anserListId);
        if(answerList == null){
            return null;
        }
        AnswerListDTO answerListDTO = new AnswerListDTO();
        answerListDTO.setAnswerDTOList(new ArrayList<AnswerDTO>());
        if(answerList == null){
            return listOfAnswer;
        }
        answerListDTO.setAnswerListId(answerListDTO.getAnswerListId());
        for (Answer answer:answerList.getAnswerDTOList()){
            AnswerDTO answerDTO=new AnswerDTO();
            BeanUtils.copyProperties( answer, answerDTO);
            answerListDTO.getAnswerDTOList().add(answerDTO);
        }        List<AnswerDTO> answerDTOS = this.getAnswerSortedByUpvotes(answerListDTO);
        for(int i=0; i<answerList.getAnswerDTOList().size() && i<3; i++){
            listOfAnswer.add(answerDTOS.get(i).getAnswer());
        }
        return listOfAnswer;
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

    @Override
    public List<AnswerDTO> getAnswerSortedByUpvotes(AnswerListDTO answerListDTO) {
        List<AnswerDTO> answerDTOs = answerListDTO.getAnswerDTOList();
        for(AnswerDTO answerDTO : answerDTOs){
            try {
                answerDTO.setUpvoteCount(upvoteListRepository.findOne(answerDTO.getId()).getUserIdList().size());
            }catch (Exception e)
            {
                answerDTO.setUpvoteCount(0);
            }

        }
        answerDTOs.sort(new Comparator());

        return answerDTOs;
    }

    public class Comparator implements java.util.Comparator<AnswerDTO> {
        @Override
        public int compare(AnswerDTO answerDTO1, AnswerDTO answerDTO2) {
            if(answerDTO1.getUpvoteCount()==answerDTO2.getUpvoteCount())
                return 0;
            else if(answerDTO1.getUpvoteCount()<answerDTO2.getUpvoteCount())
                return 1;
            else
                return -1;
        }
    }

}
