package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

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
  
  @Test
  public void basicCRUD() throws Exception {
      //given
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");
    memberJpaRepository.save(member1);
    memberJpaRepository.save(member2);
      //when
    Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
    Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
      //then
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);
    
    List<Member> all = memberJpaRepository.findAll();
    assertThat(all.size()).isEqualTo(2);
    
    long count = memberJpaRepository.count();
    assertThat(count).isEqualTo(2);
    
    memberJpaRepository.delete(member1);
    memberJpaRepository.delete(member2);
    
    long deletedCount = memberJpaRepository.count();
    assertThat(deletedCount).isEqualTo(0);
  }
}