package com.icia.member.service;

import com.icia.member.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO,  MultipartFile file) throws IOException;

    Page<BoardPagingDTO> paging(Pageable pageable);

    BoardDetailDTO findById(Long boardId);

    Long update(BoardUpdateDTO boardUpdateDTO);

    Page<BoardPagingDTO> boardSearchList(String keyword, Pageable pageable);
}
