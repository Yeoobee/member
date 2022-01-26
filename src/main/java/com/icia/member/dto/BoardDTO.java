package com.icia.member.dto;

import lombok.Data;

@Data
public class BoardDTO {
    private Long id;
    private String writer;
    private String title;
    private String content;


}
