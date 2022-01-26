package com.icia.member.service;

import com.icia.member.dto.CommentDetailDTO;
import com.icia.member.dto.CommentSaveDTO;

import java.util.List;

public interface CommentService {
    Long save(CommentSaveDTO commentSaveDTO);

    List<CommentDetailDTO> findAll(Long boardId);
}
