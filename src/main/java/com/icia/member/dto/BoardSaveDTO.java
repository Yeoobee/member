package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveDTO {
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private String fileName;
    private String filePath;
}
