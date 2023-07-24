package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
  
  @Autowired
  MemberRepository memberRepository; // 인터페이스만 해놓으면 구현체는 spring data jpa가 알아서 만들어서 주입해준다
  
  @Autowired
  TeamRepository teamRepository;
  
  @Test
  public void testMember() {
    Member member = new Member("memberA");
    Member savedMember = memberRepository.save(member);
    
    Member findMember = memberRepository.findById(savedMember.getId()).get();
    
    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    assertThat(findMember).isEqualTo(member);
  }
  
  @Test
  public void basicCRUD() throws Exception {
      //given
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");
    memberRepository.save(member1);
    memberRepository.save(member2);
      //when
    Member findMember1 = memberRepository.findById(member1.getId()).get();
    Member findMember2 = memberRepository.findById(member2.getId()).get();
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);
    
    List<Member> members = memberRepository.findAll();
    assertThat(members.size()).isEqualTo(2);
    
    long count = memberRepository.count();
    assertThat(count).isEqualTo(2);
    
    memberRepository.delete(member1);
    memberRepository.delete(member2);
    
    long deletedCount = memberRepository.count();
    assertThat(deletedCount).isEqualTo(0);
      //then
  }
  
  @Test
  public void findTest() throws Exception {
    //given
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("AAA", 20);
    //when
    memberRepository.save(m1);
    memberRepository.save(m2);
    //then
    List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
    
    assertThat(result.get(0).getUsername()).isEqualTo("AAA");
    assertThat(result.get(0).getAge()).isEqualTo(20);
  }
  
  @Test
  public void queryTest() throws Exception {
    //given
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("AAA", 20);
    //when
    memberRepository.save(m1);
    memberRepository.save(m2);
    //then
    List<Member> result = memberRepository.findMember("AAA", 15);
    
    assertThat(result.get(0).getUsername()).isEqualTo("AAA");
    assertThat(result.get(0).getAge()).isEqualTo(20);
  }
  
  @Test
  public void findUSernameListtest() throws Exception {
    
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("BBB", 20);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<String> usernameList = memberRepository.findUsernameList();
    for (String s : usernameList) {
      System.out.println("s = " + s);
    }
  }
  
  @Test
  public void findMemberDTO() throws Exception {
    
    Team team = new Team("teamA");
    teamRepository.save(team);
    
    Member m1 = new Member("AAA", 10);
    memberRepository.save(m1);
    
    List<MemberDTO> memberDTOList = memberRepository.findMemberDTO();
    for (MemberDTO dto : memberDTOList) {
      System.out.println("dto = " + dto);
    }
  }
  
  @Test
  public void findByNamesTest() throws Exception {
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("BBB", 20);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
    for (Member member : result) {
      System.out.println("s = " + member);
    }
  }
  
  @Test
  public void returnTypeTest() throws Exception {
    Member m1 = new Member("AAA", 10);
    Member m2 = new Member("BBB", 20);
    
    memberRepository.save(m1);
    memberRepository.save(m2);
    
    List<Member> aaa = memberRepository.findListByUsername("AAA");
    Member bbb = memberRepository.findMemberByUsername("AAA");
    Optional<Member> ccc = memberRepository.findOptionalByUsername("AAA"); // 밑의 문제를 해결하기 위한 Optional!
    // 만약 m1, m2의 이름이 모두 AAA 라면 여기도 Exception이 터졌을 것!
    // 단건 조회에서 여러개의 결과를 반환하게 되면 NonUniqueResultException(JPA 오류) -> IncorrectResultSizeDataAccessException(Spring 오류)으로 변환해서 보여준다
    
    /**
     *  참고: 단건으로 지정한 메서드를 호출하면 스프링 데이터 JPA는 내부에서 JPQL의 Query.getSingleResult() 메서드를 호출한다.
     *  이 메서드를 호출했을 때 조회 결과가 없으면 javax.persistence.NoResultException 예외가 발생하는데 개발자 입장에서 다루기가 상당히 불편하다.
     *  스프링 데이터 JPA는 단건을 조회할 때 이 예외가 발생하면 예외를 무시하고 대신에 null 을 반환한다.
     *  컬렉션
     *    결과 없음: 빈 컬렉션 반환
     *  단건 조회
     *    결과 없음: null 반환
     *    결과가 2건 이상: javax.persistence.NonUniqueResultException 예외 발생
     */
    
  }
}