package com.icia.member.controller;

import com.icia.member.dto.CommentDetailDTO;
import com.icia.member.dto.CommentSaveDTO;
import com.icia.member.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService cs;

    @PostMapping("/save")
    public @ResponseBody List<CommentDetailDTO> save(@ModelAttribute CommentSaveDTO commentSaveDTO, Model model) {
        cs.save(commentSaveDTO);
        List<CommentDetailDTO> commentList = cs.findAll(commentSaveDTO.getBoardId());
        model.addAttribute("commentList",commentList);
        return commentList;
    }
}
