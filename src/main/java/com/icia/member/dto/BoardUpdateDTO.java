package com.icia.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardUpdateDTO {
    private Long boardId;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private LocalDateTime boardDate;

    public BoardUpdateDTO(Long boardId, String boardWriter, String boardTitle, String boardContents) {
        this.boardId = boardId;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
    }
}
