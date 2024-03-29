package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
  
  @PersistenceContext
  EntityManager em;
  
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
  
  @Test
  public void paging() throws Exception {
    //given
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 10));
    memberRepository.save(new Member("member3", 10));
    memberRepository.save(new Member("member4", 10));
    memberRepository.save(new Member("member5", 10));
    
    int age = 10;
    PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
    // PageRequest : Pageable 인터페이스의 구현체중 하나. 주로 이걸 많이 쓴다.
    
    //when
    Page<Member> page = memberRepository.findByAge(age, pageRequest); // Page 타입에는 totalCount도 count 쿼리를 날리기 때문에 같이 가져온다.
    Page<MemberDTO> toMap = page.map(member -> new MemberDTO(member.getId(), member.getUsername(), null));
    // 엔티티를 그대로 반환(Member로)하기보다는 DTO로 감싸서 보내기! map을 써서 가능하다.
    
    Slice<Member> page2 = memberRepository.findSliceByAge(age, pageRequest);
    
    //then
    List<Member> content = page.getContent();
    long totalElements = page.getTotalElements();
    
    for (Member member : content) {
      System.out.println("member = " + member);
    }
    System.out.println("totalElements = " + totalElements);
    
    assertThat(content.size()).isEqualTo(3);
    assertThat(page.getTotalElements()).isEqualTo(5);
    assertThat(page.getNumber()).isEqualTo(0);
    assertThat(page.getTotalPages()).isEqualTo(2);
    assertThat(page.isFirst()).isTrue();
    assertThat(page.hasNext()).isTrue();
    
    assertThat(content.size()).isEqualTo(3);
    assertThat(page2.getNumber()).isEqualTo(0);
    assertThat(page2.isFirst()).isTrue();
    assertThat(page2.hasNext()).isTrue();
    
  }
  
  @Test
  public void bulkUpdate() {
    //given
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 19));
    memberRepository.save(new Member("member3", 20));
    memberRepository.save(new Member("member4", 21));
    memberRepository.save(new Member("member5", 40));
    
    //when
    int resultCount = memberRepository.bulkAgePlus(20);
//    em.clear();
//    clear를 해줘야 영속성 컨텍스트에도 반영된다.
//    -> 우선 bulk 연산을 하고 DB에 반영시킨 후, 영속성 컨텍스트를 강제로 비워서 다음에 찾아올 때 DB에서 끌어와 영속성 컨텍스트에 저장한 뒤 찾아오도록 한다.
    
    List<Member> members = memberRepository.findListByUsername("member5");
    Member member5 = members.get(0);
    System.out.println("member5 = " + member5); // 40살로 나온다.
    // 벌크 연산은 영속성 컨텍스트를 무시하고 DB에 쿼리를 날려버리기 때문에 save에서 persist 된 것만 찾아올 수 있다.
    
    //then
    assertThat(resultCount).isEqualTo(3);
  }
}