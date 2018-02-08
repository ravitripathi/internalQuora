package com.example.QuestionAnswerAPI.QuestionAnswerAPI.controller;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.UpvoteDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.response.CountDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.AnswerService;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.UpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.QuestionAnswerAPI.QuestionAnswerAPI.URL.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = QAAPI)
public class UpvoteController {
    @Autowired
    UpvoteService upvoteService;

    /*//  For adding an upvote int database API
    //    input - post method and details of upvote(answerId, userId) to add into upvote database of a particular answer
    //    output - upvote will be added into database and status will be returns*/
    @RequestMapping(method = RequestMethod.POST, value = ADDUPVOTE)
    public ResponseEntity<?> addUpvote(@RequestBody UpvoteDTO upvoteDTO) { //upvoteId of postdto will be same as answerId
        System.out.println(upvoteDTO.toString());
        int flag = upvoteService.addUpvote(upvoteDTO);
        if (flag == 1) {
            return new ResponseEntity<>("Upvote Removed", HttpStatus.OK);
        }
        return new ResponseEntity<>("Upvote Added", HttpStatus.CREATED);
    }

    /*//  For getting count of the upvotes stored in database of a particular answer
    //   input : userId
    //   output : countDTO will be returned and count of questions of particular user*/
    @RequestMapping(method = RequestMethod.GET, value = COUNTUPVOTESBYANSWERID)
    public ResponseEntity<?> getCountUpvotesByAnswerId(@PathVariable("answerId") String answerId) {
        Integer commentCount = upvoteService.countUpvotesByAnswerId(answerId);
        CountDTO countDTO = new CountDTO();
        countDTO.setCountByUserId(commentCount);
        return new ResponseEntity<>(countDTO, HttpStatus.OK);
    }

    /*//  For getting upvoted or not  of the upvotes stored in database of a particular answer
    //   input : userId
    //   output : countDTO will be returned and count of questions of particular user*/
    @RequestMapping(method = RequestMethod.POST, value = GETUPVOTEBYUSERID)
    public ResponseEntity<?> getUpvoteByUserId(@RequestBody UpvoteDTO upvoteDTO) {
        Boolean isuserUpvoted = upvoteService.isUpvotedByUser(upvoteDTO);
        return new ResponseEntity<>(isuserUpvoted, HttpStatus.OK);
    }

}
