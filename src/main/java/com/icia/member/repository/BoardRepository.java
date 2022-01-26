package com.icia.member.repository;


import com.icia.member.entity.BoardEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // 조회수
    // jpql(java persistence query language)
    // query dsl
    // 반드시 테이블에 대한 약칭을 써야 함.
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits+1 where b.id = :boardId")
    int boardHits(@Param("boardId") Long boardId);

    Page<BoardEntity> findByBoardTitleContaining(String keyword, Pageable pageable);

}
