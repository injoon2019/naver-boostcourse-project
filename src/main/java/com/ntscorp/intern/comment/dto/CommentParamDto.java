package com.ntscorp.intern.comment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentParamDto {
    private int productId;
    private int reservationInfoId;
    private int score;
    private String comment;
    private int commentId;
    private int fileId;
}
