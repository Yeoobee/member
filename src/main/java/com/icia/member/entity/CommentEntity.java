package com.icia.member.entity;

import com.icia.member.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String commentWriter;

    @Column
    private String commentContents;
    // 회원 엔티티 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


    // 원글에 게시글 번호를 참조하기 위한 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // 부모테이블(참조하고자하는 테이블)의 pk 컬럼이름
    private BoardEntity boardEntity; // 참조하고자 하는 테이블을 관리하는 엔티티

    public static CommentEntity toSaveEntity(CommentSaveDTO commentSaveDTO, BoardEntity boardEntity, MemberEntity memberEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentSaveDTO.getCommentWriter());
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setMemberEntity(memberEntity);
        return commentEntity;


    }



}
