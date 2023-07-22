package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {
  
  @Autowired
  MemberJpaRepository memberJpaRepository;
  
  @Test
  public void testMember() throws Exception {
      //given
    Member member = new Member("memberA");
    Member savedMember = memberJpaRepository.save(member);
      //when
    Member findMember = memberJpaRepository.find(savedMember.getId());
      //then
    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    assertThat(findMember).isEqualTo(member); // true : 영속성 컨텍스트에 의해 같은 인스턴스임을 보장함. 같은 트랜잭션이기 때문!
  }
}