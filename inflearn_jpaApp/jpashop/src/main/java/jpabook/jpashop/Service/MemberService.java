package jpabook.jpashop.Service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA의 모든 데이터 변경이나 로직들은 가급적이면 트랜잭션 안에서 실행되어야함. 읽기 전용이 많으므로 readOnly 써놓음
@RequiredArgsConstructor
public class MemberService {
  
//  @Autowired
//  private MemberRepository memberRepository; -> 필드 DI

//  생성자를 통한 DI
  private final MemberRepository memberRepository;
//  public MemberService(MemberRepository memberRepository) {
//    this.memberRepository = memberRepository;
//  }
  // @Autowired 없어도 자동 의존성 주입. @RequiredArgsConstructor를 통해 final인 변수만 생성자를 생성해주면 자동적으로 생성자를 통한 DI 가능
  
  // 회원 가입
  @Transactional // 여기는 읽기 전용이 아니므로 따로 @Transactional 기입
  public Long join(Member member) {
    validateDuplicateMember(member); // 중복 회원 검증
    memberRepository.save(member);
    return member.getId();
  }
  
  private void validateDuplicateMember(Member member) {
    List<Member> findMembers = memberRepository.findByName(member.getName());
    // 멀티쓰레드같은 것을 고려하여 name을 유니크로 잡아주는게 안전함.
    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }
  
  // 회원 전체 조회
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }
  
  public Member findOne(Long memberId) {
    return memberRepository.findById(memberId).get();
  }
  
  @Transactional
  public void update(Long id, String name) {
    Member member = memberRepository.findById(id).get(); // 영속성 컨텍스트에 저장 -> member가 영속상태
    member.setName(name); // 변경 감지 이용! 영속 상태의 member의 상태 변경 -> commit 시에 영속성컨텍스트 flush하고 DB에 commit
    
  }
}
