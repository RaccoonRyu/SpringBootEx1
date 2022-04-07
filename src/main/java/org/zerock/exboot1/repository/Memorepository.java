package org.zerock.exboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.exboot1.entity.MemoDTO;

/**
 * Memorepository
 * - MemoDTO를 JPA로 처리하기 위한 레포지토리
 *
 * @author 류지헌
 * @created 2022-04-07
 */
public interface Memorepository extends JpaRepository<MemoDTO, Long> {
}
