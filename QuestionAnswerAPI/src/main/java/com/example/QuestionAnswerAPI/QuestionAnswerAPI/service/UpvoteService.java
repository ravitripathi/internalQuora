package com.example.QuestionAnswerAPI.QuestionAnswerAPI.service;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.UpvoteDTO;

public interface UpvoteService {
    Integer countUpvotesByAnswerId(String answerId);  //    count the total upvotes of a particular answer
    int addUpvote(UpvoteDTO upvoteDTO); //    to add an upvote of a particular answer into upvote database
    //    0-not in db : 1-already upvoted
    public boolean isUpvotedByUser(UpvoteDTO upvoteDTO);    //    to get upvoted by or not
}
