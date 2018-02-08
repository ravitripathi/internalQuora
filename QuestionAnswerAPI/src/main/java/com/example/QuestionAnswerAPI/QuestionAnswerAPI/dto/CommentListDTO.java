package com.example.QuestionAnswerAPI.QuestionAnswerAPI.dto;

import java.util.List;

public class CommentListDTO {
    private String commentListId;
    private List<CommentDTO> commentDTOList;

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
