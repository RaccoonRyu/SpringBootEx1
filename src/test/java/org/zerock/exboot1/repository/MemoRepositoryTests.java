package org.zerock.exboot1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.exboot1.entity.MemoDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * MemoRepositoryTests
 * - main에서 작성한 repository 테스트용 클래스
 *
 * @author 류지헌
 * @created 2022-04-07
 */
@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    Memorepository memorepository;

    @Test
    // Memorepository가 정상적인 실제 객체로 잘 생성되는지 확인
    public void testClass(){
        System.out.println(memorepository.getClass().getName());
    }

    @Test
    // Memorepository가 새로 생성된 100개의 Memo객체를 정상적으로 insert하는지 확인
    public void testInsertDummieDatas() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            MemoDTO memoDTO = MemoDTO.builder().memoText("Memo Sample"+i).build();
            memorepository.save(memoDTO);
        });
    }

    @Test
    // Memorepository가 하나의 엔티티 객체를 정상적으로 select하는지 확인
    public void testSelect() {
        Long mno = 100L; // mno가 100번인 객체를 select

        Optional<MemoDTO> result = memorepository.findById(mno);
        
        // findById()는 Optional 타입을 반환하기 때문에 반환 결과를 한번 더 체크
        if(result.isPresent()) { // 반환된 결과가 존재하는 경우
            MemoDTO memoDTO = result.get(); // 반환된 결과를 memoDTO에 초기화
            System.out.println(memoDTO); // 초기화 후 확인
        }
    }

    @Test
    // Memorepository가 1개의 Memo객체를 정상적으로 update하는지 확인
    public void testUpdate() {
        MemoDTO memoDTO = MemoDTO.builder().mno(100L).memoText("Update Text").build(); // mno가 100번인 객체를 update
        System.out.println(memorepository.save(memoDTO));
    }

    @Test
    // Memorepository가 1개의 Memo객체를 정상적으로 delete하는지 확인
    public void testDelete() {
       Long mno = 100L; // mno가 100번인 객체를 삭제
       memorepository.deleteById(mno);
    }

    @Test
    // 페이지 처리와 정렬이 정상적으로 이뤄지는지 확인
    public void testPageDefault() {

        // 1페이지당 10개의 데이터
        Pageable page = PageRequest.of(0,10); // 페이지 처리는 반드시 0부터 시작됨을 주의!

        Page<MemoDTO> result = memorepository.findAll(page);
        System.out.println(result); // 현재 데이터가 199개 이므로 20page가 출력

        System.out.println("================================================================");

        System.out.println("Total Pages : "+result.getTotalPages()); // 총 페이지 수
        System.out.println("Total Count : "+result.getTotalElements()); // 전체 페이지 개수
        System.out.println("Page Number : "+result.getNumber()); // 현재 페이지 번호(0부터 시작)
        System.out.println("Page Size : "+result.getSize()); // 페이지당 데이터 개수
        System.out.println("Has next page? : "+result.hasNext()); // 다음 페이지 존재 여부
        System.out.println("First page? : "+result.isFirst()); // 시작 페이지(0) 여부
    }

    @Test
    // 페이징에 정렬 조건이 정상적으로 추가되었는지 테스트
    public void testSort() {
        
        Sort sort1 = Sort.by("mno").descending(); // mno 필드 기준 내림차순의 정렬조건 설정

        Pageable page = PageRequest.of(0, 10, sort1);

        Page<MemoDTO> result = memorepository.findAll(page);

        result.get().forEach(memoDTO -> {
            System.out.println(memoDTO);
        });
    }

    @Test
    // 레포지토리에 작성한 쿼리 메서드가 정상작동하는지 테스트
    public void testQueryMethods() {
        List<MemoDTO> memoDTOList = memorepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for(MemoDTO memo : memoDTOList) {
            System.out.println(memo);
        }
    }

    @Test
    // 레포지토리에 작성한 페이저블 사용 쿼리 메서드가 정상작동하는지 테스트
    public void testQueryMethodWithPageable() {
        Pageable page = PageRequest.of(0, 10, Sort.by("mno").descending()); // mno기준 내림차순 정렬 조건

        Page<MemoDTO> result = memorepository.findByMnoBetween(10L,50L,page);

        result.get().forEach(memo -> System.out.println(memo));
    }

    @Commit // DB에 최종 결과를 커밋하기 위해 사용 -> delete는 기본적으로 롤백 처리되므로 결과 반영이 필요하다.
    @Transactional // select와 delete가 함께 이뤄지기 때문에 둘 중 하나라도 정상처리 되지 않을 경우 롤백을 위해 사용
    @Test
    // 레포지토리에 작성한 삭제 쿼리 메서드가 정상작동하는지 테스트
    public void testDeleteQueryMethods() {
        
        memorepository.deleteMemoByMnoLessThan(10L); // mno이 10보다 작은 데이터 삭제
    }
}
