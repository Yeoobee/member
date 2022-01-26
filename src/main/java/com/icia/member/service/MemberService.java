package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;

import java.util.List;

public interface MemberService {
    void save(MemberSaveDTO memberSaveDTO);

    boolean login(MemberLoginDTO memberLoginDTO);

    List<MemberDetailDTO> findAll();

    void deleteById(Long memberId);

    MemberDetailDTO findById(Long memberId);

    MemberDetailDTO findByEmail(String memberEmail);

    Long update(MemberDetailDTO memberDetailDTO);
}
