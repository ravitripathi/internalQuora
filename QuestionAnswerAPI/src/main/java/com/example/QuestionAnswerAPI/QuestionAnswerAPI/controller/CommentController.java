package com.example.QuestionAnswerAPI.QuestionAnswerAPI.controller;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.CommentDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.CommentListDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.Comment;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.CommentList;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.response.CountDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.response.SuccessDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.AnswerService;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.CommentService;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.impl.RestTemplateServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.QuestionAnswerAPI.QuestionAnswerAPI.URL.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = QAAPI)
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    RestTemplateServiceImpl restTemplateService;

    /*//  For adding a comment int database API
    //    input - post method and details of comment to add into comment database of a particular answer
    //    output - comment will be added into database and status will be returned*/
    @RequestMapping(method = RequestMethod.POST, value = ADDCOMMENT)
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO) { //commentId of postdto will be same as answerId
        System.out.println(commentDTO);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        String cmtId = UUID.randomUUID().toString().replace("-", "");
        comment.setCommentId(cmtId);
        commentService.addComment(comment);
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(true);
        return new ResponseEntity<SuccessDTO>(successDTO, HttpStatus.CREATED);
    }

    /*//  For getting all the comments of an answer stored in database
    //    input - Get method
    //    output - commentlist contains list of comments of answer*/
    @RequestMapping(method = RequestMethod.GET, value = GETCOMMENTSBYANSWERID)
    public ResponseEntity<?> getCommentsByAnswerId(@PathVariable("answerId") String answerId) {
        CommentList commentList = commentService.findByCommentlistId(answerId);
        CommentListDTO commentListDTO = new CommentListDTO();
        if(commentList == null){
            return new ResponseEntity<>(commentListDTO, HttpStatus.OK);
        }
        BeanUtils.copyProperties(commentList, commentListDTO);
        List<CommentDTO> listOfComment = new ArrayList<>();
        List<CommentDTO> listOfCommentDTO = new ArrayList<>();
        listOfComment = commentListDTO.getCommentDTOList();
        for(CommentDTO commentDTO : listOfComment){
            String responseUserName = restTemplateService.addCommentUser(commentDTO);
            commentDTO.setUserName(responseUserName);
            listOfCommentDTO.add(commentDTO);
        }
        commentListDTO.setCommentDTOList(listOfCommentDTO);
        return new ResponseEntity<>(commentListDTO, HttpStatus.OK);
    }

    /*//  For getting count of the comments stored in database of a particular answer
    //   input : userId
    //   output : countDTO will be returned and count of questions of particular user*/
    @RequestMapping(method = RequestMethod.GET, value = COUNTCOMMENTSBYANSWERID)
    public ResponseEntity<?> getCountCommentsByAnswerId(@PathVariable("answerId") String answerId) {
        Integer commentCount = commentService.countCommentsByAnswerId(answerId);
        CountDTO countDTO = new CountDTO();
        countDTO.setCountByUserId(commentCount);
        return new ResponseEntity<>(countDTO, HttpStatus.OK);
    }
}
