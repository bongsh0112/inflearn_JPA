package jpabook.jpashop.Service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest // 이 두개는 SpringBoot와 연동하여 테스트 하겠다는 것.
@Transactional // 데이터를 롤백시키기 위한 Transacitonal 사용
// 이 어노테이션으로 인해 같은 트랜잭션 내에서 같은 Pk를 가지고 있는 엔티티는 같은 영속성 컨텍스트안에 들어가기 때문에 test가 가능하다!!!
public class MemberServiceTest {
  
  @Autowired
  MemberService memberService;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  EntityManager em;
  
  @Test
  public void 회원가입() throws Exception {
    //given
    Member member = new Member();
    member.setName("kim");
    
    //when
    Long saveId = memberService.join(member); // 여기서 MemberRepository를 통해 persist 수행.
    
    //then
//    em.flush(); // insert 쿼리가 나간다.
    assertEquals(member, memberRepository.findOne(saveId));
  }
  
  @Test(expected = IllegalStateException.class)
  public void 중복_회원_예외() throws Exception {
    //given
    Member member1 = new Member();
    member1.setName("kim1");
    
    Member member2 = new Member();
    member2.setName("kim1");
    
    //when
    memberService.join(member1);
    memberService.join(member2);
    
    //then
    System.out.println("예외가 발생해야 한다.");
  }
  
}