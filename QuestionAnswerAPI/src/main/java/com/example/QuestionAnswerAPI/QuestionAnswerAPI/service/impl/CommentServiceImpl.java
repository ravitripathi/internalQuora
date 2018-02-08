package com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.impl;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.CommentDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.CommentListDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.Comment;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity.CommentList;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.repository.CommentListRepository;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentListRepository commentListRepository;

//    find all comments by answerId
    @Override
    public CommentList findByCommentlistId(String answerId) {
        return commentListRepository.findOne(answerId);
    }

//    count the total comments of a particular answer
    @Override
    public Integer countCommentsByAnswerId(String answerId) {
        if(commentListRepository.getByCommentListId(answerId) == null){
            return 0;
        }
        return (commentListRepository.getByCommentListId(answerId).getCommentDTOList().size());
    }

//    to add a comment in to database
    @Override
    public void addComment(Comment comment){
        comment.setTimestamp(Date.from(Instant.now()).toString());
        CommentDTO commentDTO  = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        System.out.println(commentDTO);
        System.out.println(commentDTO.getComment());
        CommentListDTO commentListDTO;
        CommentList commentList;
        commentList = commentListRepository.getByCommentListId((commentDTO.getAnswerId()));
        if( commentList == null || commentList.getCommentDTOList() == null) {
            commentList= new CommentList();
            commentListDTO = new CommentListDTO();
            List<CommentDTO> commentDTOList = new ArrayList<>();
            commentDTOList.add(commentDTO);
            commentListDTO.setCommentDTOList(commentDTOList);
            commentListDTO.setCommentListId(comment.getCommentId());
            BeanUtils.copyProperties(commentListDTO, commentList);
        }
        else{
            commentList = commentListRepository.getByCommentListId(commentDTO.getAnswerId());
            commentListDTO = new CommentListDTO();
            BeanUtils.copyProperties(commentList, commentListDTO);
            commentListDTO.getCommentDTOList().add(commentDTO);
            //commentDTO.setCommentId(String.valueOf(commentListDTO.getCommentDTOList().size()));
            BeanUtils.copyProperties(commentListDTO, commentList);
        }
        //commentList.setCommentListId(comment.getCommentId());
        commentList.setCommentListId(comment.getAnswerId());
        commentListRepository.save(commentList);
        return;
    }
}
