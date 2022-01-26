package com.icia.member.service;

import com.icia.member.common.PagingConst;
import com.icia.member.dto.*;
import com.icia.member.entity.BoardEntity;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.BoardRepository;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository br;
    private final MemberRepository mr;

    @Override
    public Long save(BoardSaveDTO boardSaveDTO, MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files" ;

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        MemberEntity memberEntity = mr.findByMemberEmail(boardSaveDTO.getBoardWriter());
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardSaveDTO, memberEntity);

        boardEntity.setFileName(fileName);
        boardEntity.setFilePath("/files/" + fileName);

        Long boardId = br.save(boardEntity).getId();
        return boardId;
    }

    @Override
    public Page<BoardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page -1;
        page = (page ==1)? 0: (page-1);
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Page<BoardEntity> => Page<BoardPagingDTO>
        Page<BoardPagingDTO> boardList = boardEntities.map(
                board -> new BoardPagingDTO(board.getId(),
                        board.getBoardWriter(),
                        board.getBoardTitle())
        );
        return boardList;
    }
//    @Override
//    public Page<BoardPagingDTO> paging(Pageable pageable) {
//        int page = pageable.getPageNumber();
//        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
////        page = page -1;
//        page = (page ==1)? 0: (page-1);
//        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
//        // Page<BoardEntity> => Page<BoardPagingDTO>
//        Page<BoardPagingDTO> boardList = boardEntities.map(
//                board -> new BoardPagingDTO(board.getId(),
//                        board.getBoardWriter(),
//                        board.getBoardTitle())
//        );
//        return boardList;
//    }



    @Override
    public BoardDetailDTO findById(Long boardId) {
        Optional<BoardEntity> boardEntityOptional = br.findById(boardId);
        BoardDetailDTO boardDetailDTO = null;

        if (boardEntityOptional.isPresent()) {
            BoardEntity boardEntity = boardEntityOptional.get();
            boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);
        }
        return boardDetailDTO;
    }

    @Override
    public Long update(BoardUpdateDTO boardUpdateDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardUpdateDTO);
        Long boardId = br.save(boardEntity).getId();
        return boardId;
    }

    @Override
    public Page<BoardPagingDTO> boardSearchList(String keyword, Pageable pageable) {
        Page<BoardEntity> boardEntityPage = br.findByBoardTitleContaining(keyword, pageable);
        Page<BoardPagingDTO> boardList =boardEntityPage.map(
                board -> new BoardPagingDTO(board.getId(),
                        board.getBoardWriter(),
                        board.getBoardTitle())
        );
        return boardList;
    }


}
