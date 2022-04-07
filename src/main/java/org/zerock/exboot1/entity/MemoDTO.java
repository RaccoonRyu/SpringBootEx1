package org.zerock.exboot1.entity;

import lombok.*;

import javax.persistence.*;

/**
 * MemoDTO
 * - JPA를 통해 관리하게 될 Memo 객체 DTO
 *
 * @author 류지헌
 * @created 2022-04-07
 */
@Entity // 해당 클래스가 JPA에서 관리될 엔티티 객체라는 것을 명시
@Table(name="tbl_memo") // 엔티티 클래스를 DB상 어떤 테이블로 생성할지 명시
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemoDTO {

    @Id // DB의 PK에 해당하는 필드 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 키 생성전략. PK 자동생성시 사용. IDENTITY는 DB가 키생성을 결정한다.
    private Long mno; // 메모 번호
    
    @Column(length = 200, nullable = false) // DB의 컬럼에 해당하는 엔티티 클래스의 정보
    private String memoText; // 메모 내용
}
