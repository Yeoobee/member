package com.icia.member.dto;

import lombok.Data;

@Data
public class CommentSaveDTO {
    private Long boardId;
    private String commentWriter;
    private String commentContents;
}
