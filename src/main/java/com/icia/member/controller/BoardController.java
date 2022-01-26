package com.icia.member.controller;

import com.icia.member.common.PagingConst;
import com.icia.member.dto.*;
import com.icia.member.entity.BoardEntity;
import com.icia.member.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static com.icia.member.common.SessionConst.LOGIN_EMAIL;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
private final BoardService bs;
    @GetMapping("/save")
    public String saveForm() {
        return "board/save";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO, MultipartFile file) throws IOException {
        Long boardId = bs.save(boardSaveDTO, file);
        return "redirect:/board/";
    }



//    // 페이징처리된 글 목록 (검색키워드 없을때)
//    @GetMapping
//    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
//        Page<BoardPagingDTO> boardList = bs.paging(pageable);
//        model.addAttribute("boardList", boardList);
//        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
//        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        return "board/findAll";
//    }
    // 페이징처리된 글 목록 (검색키워드 없을때)
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, String keyword){

        Page<BoardPagingDTO> boardList = null;

        if (keyword == null){
            boardList = bs.paging(pageable);
        } else {
            boardList = bs.boardSearchList(keyword, pageable);
        }

        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/findAll";
    }



    // 글 상세정보
    @GetMapping("/{boardId}")
    public String findById(@PathVariable Long boardId, Model model) {
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board", board);
        return "board/findById";
    }

    // 업데이트폼
    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model){
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board",board);
        return "board/update";
    }


    // 업데이트 put
    @PutMapping("/{boardId}")
    public ResponseEntity update(@RequestBody BoardUpdateDTO boardUpdateDTO){
        bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }



}
