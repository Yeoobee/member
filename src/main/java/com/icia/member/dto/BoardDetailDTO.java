package com.icia.member.dto;

import com.icia.member.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDTO {
    private Long boardId;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private LocalDateTime boardDate;
    private String fileName;
    private String filePath;

    public static BoardDetailDTO toBoardDetailDTO(BoardEntity boardEntity){
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        boardDetailDTO.setBoardId(boardEntity.getId());
        boardDetailDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDetailDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDetailDTO.setBoardContents(boardEntity.getBoardContents());
        boardDetailDTO.setBoardDate(boardEntity.getCreateTime());
        boardDetailDTO.setFileName(boardEntity.getFileName());
        boardDetailDTO.setFilePath(boardEntity.getFilePath());
        return boardDetailDTO;
    }
}
