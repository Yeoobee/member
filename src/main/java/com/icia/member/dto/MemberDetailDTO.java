package com.icia.member.dto;

import com.icia.member.entity.MemberEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDetailDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private LocalDateTime createTime;

    public static MemberDetailDTO toMemberDetailDTO(MemberEntity memberEntity){
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberId(memberEntity.getId());
        memberDetailDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDetailDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDetailDTO.setMemberName(memberEntity.getMemberName());
        memberDetailDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDetailDTO.setCreateTime(memberEntity.getCreateTime());
        return memberDetailDTO;
    }
}
