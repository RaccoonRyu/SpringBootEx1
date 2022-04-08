package org.zerock.exboot1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.exboot1.entity.MemoDTO;

import java.util.List;

/**
 * Memorepository
 * - MemoDTO를 JPA로 처리하기 위한 레포지토리
 *
 * @author 류지헌
 * @created 2022-04-07
 */
public interface Memorepository extends JpaRepository<MemoDTO, Long> {

    // 쿼리 메서드 사용
    List<MemoDTO> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    // 쿼리 메서드에 페이저블 사용
    Page<MemoDTO> findByMnoBetween(Long from, Long to, Pageable page);
    
    // 파라미터보다 작은 메모번호의 데이터를 삭제하는 쿼리 메서드
    void deleteMemoByMnoLessThan(Long num);
}
