package com.example.QuestionAnswerAPI.QuestionAnswerAPI.entity;

import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.CommentDTO;
import com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto.CommentListDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = CommentList.COLLECTION_NAME)
public class CommentList {

    public static final String COLLECTION_NAME = "comment";

    @Id
    private String commentListId;   //id will be same as answer id
    private List<CommentDTO> commentDTOList;

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public String getCommentListId() {
        return commentListId;
    }

    public void setCommentListId(String commentListId) {
        this.commentListId = commentListId;
    }

    public List<CommentDTO> getCommentDTOList() {
        return commentDTOList;
    }

    public void setCommentDTOList(List<CommentDTO> commentDTOList) {
        this.commentDTOList = commentDTOList;
    }
}