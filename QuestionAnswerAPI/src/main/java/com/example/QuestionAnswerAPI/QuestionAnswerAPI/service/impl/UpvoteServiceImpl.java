package com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.impl;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.UpvoteDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.UpvoteList;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository.UpvoteListRepository;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.UpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpvoteServiceImpl implements UpvoteService{
    @Autowired
    UpvoteListRepository upvoteListRepository;

//    count the total upvotes of a particular answer
    @Override
    public Integer countUpvotesByAnswerId(String answerId) {
        if(upvoteListRepository.getByUpvoteListId(answerId) == null){
            return 0;
        }
        return (upvoteListRepository.getByUpvoteListId(answerId).getUserIdList().size());
    }

//    to add an upvote of a particular answer into upvote database
    @Override
    public int addUpvote(UpvoteDTO upvoteDTO){
        UpvoteList upvoteList = upvoteListRepository.findOne(upvoteDTO.getUpvoteId());
        if(upvoteList == null){
            upvoteList = new UpvoteList();
            upvoteList.setUpvoteListId(upvoteDTO.getUpvoteId());
            List<String> userIdList = new ArrayList<>();
            userIdList.add(upvoteDTO.getUserId());
            upvoteList.setUserIdList(userIdList);
            upvoteListRepository.save(upvoteList);
            return 0;
        }
        for(String userId : upvoteList.getUserIdList()){
            if(upvoteDTO.getUserId().equals(userId)){
                List<String> userIdList;
                userIdList = upvoteList.getUserIdList();
                userIdList.remove(userId);
                upvoteList.setUserIdList(userIdList);
                upvoteListRepository.save(upvoteList);
                return 1;
            }
        }
        List<String> userIdList;
        upvoteList.getUserIdList().add(upvoteDTO.getUserId());
        userIdList = upvoteList.getUserIdList();
        upvoteList.setUserIdList(userIdList);
        upvoteListRepository.save(upvoteList);
        return 0;
    }

//    to get upvoted by or not
    public boolean isUpvotedByUser(UpvoteDTO upvoteDTO){
        UpvoteList upvoteList = upvoteListRepository.findOne(upvoteDTO.getUpvoteId());
        if(upvoteList == null){
            return false;
        }
        if(upvoteList.getUserIdList().contains(upvoteDTO.getUserId())){
            return true;
        }
        return false;
    }
}
