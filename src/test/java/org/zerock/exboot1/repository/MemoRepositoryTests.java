package org.zerock.exboot1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.exboot1.entity.MemoDTO;

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
}
