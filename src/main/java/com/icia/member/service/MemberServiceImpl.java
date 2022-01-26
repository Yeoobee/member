package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository mr;

    @Override
    public void save(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
        MemberEntity emailCheckResult = mr.findByMemberEmail(memberSaveDTO.getMemberEmail());
        if (emailCheckResult != null) {
            throw new IllegalStateException("중복된 이메일입니다.");
        } else {
            mr.save(memberEntity);
        }
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        if (memberEntity != null){
            if (memberEntity.getMemberPassword().equals(memberLoginDTO.getMemberPassword())){
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDetailDTO> memberList = new ArrayList<>();
        for (MemberEntity m: memberEntityList) {
            memberList.add(MemberDetailDTO.toMemberDetailDTO(m));
        }
        return memberList;
    }

    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId);
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId);
        MemberEntity memberEntity = memberEntityOptional.get();
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public MemberDetailDTO findByEmail(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public Long update(MemberDetailDTO memberDetailDTO) {
        // update 처리시 save 메서드 호출
        // MemberDetailDTO -> MemberEntity
        MemberEntity memberEntity = MemberEntity.toUpdateMember(memberDetailDTO);
        Long memberId = mr.save(memberEntity).getId();
        return memberId;
    }
}
