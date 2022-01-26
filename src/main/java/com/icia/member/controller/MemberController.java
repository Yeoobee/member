package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.icia.member.common.SessionConst.LOGIN_EMAIL;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService ms;

    @GetMapping("/save")
    public String saveForm(Model model){
        model.addAttribute("member", new MemberSaveDTO());
        return "member/save";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("member") MemberSaveDTO memberSaveDTO, BindingResult bindingResult){
        try{
            ms.save(memberSaveDTO);
        } catch (IllegalStateException e){
            // e.getMessage()에는 서비스에서 지정한 예외메시지가 담겨있음.
            bindingResult.reject("emailCheck", e.getMessage());
            return "member/save";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "member/login";
    }
    @PostMapping("/login")
    public String index(@ModelAttribute MemberLoginDTO memberLoginDTO, HttpSession session){
        boolean loginResult = ms.login(memberLoginDTO);
        if (loginResult) {
            session.setAttribute(LOGIN_EMAIL, memberLoginDTO.getMemberEmail());
            return "redirect:/";
        } else {
            return "member/login";
        }

    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    // 회원목록
    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDetailDTO> memberList = ms.findAll();
        model.addAttribute("memberList", memberList);
        return "member/findAll";
    }

    // 회원상세조회(ajax)
    @PostMapping("{memberId}")
    public @ResponseBody MemberDetailDTO detail(@PathVariable Long memberId) {
        MemberDetailDTO member = ms.findById(memberId);
        return member;
    }


    // 회원삭제(ajax)
    @DeleteMapping("{memberId}")
    public ResponseEntity deleteById2(@PathVariable Long memberId) {
        ms.deleteById(memberId);
        /*
            // 단순 화면 출력이 아닌 데이터를 리턴하고자할 때 사용하는 리턴방식
            ResponseEntity: 데이터 & 상태코드(200, 400, 404, 405, 500 등)를 함께 리턴할 수 있음.
            @ResponseBody: 데이터를 리턴할 수 있음.
        */
        // 200 코드를 리턴(오류없을때 나오는 코드)
        return new ResponseEntity(HttpStatus.OK);
    }


    // 정보수정폼
    @GetMapping("update")
    public String updateForm(Model model, HttpSession session){
        String memberEmail = (String) session.getAttribute(LOGIN_EMAIL);
        MemberDetailDTO member = ms.findByEmail(memberEmail);
        model.addAttribute("member", member);
        return "/member/update";
    }

    // 수정처리(put)
    @PutMapping("{memberId}")
    // json 으로 데이터가 전달되면 @RequestBody
    public ResponseEntity update(@RequestBody MemberDetailDTO memberDetailDTO) {
        Long memberId = ms.update(memberDetailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

}
