package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Spring Bean으로 등록
@RequiredArgsConstructor
public class MemberRepositoryOld {
  
//  @PersistenceContext
//  private EntityManager em;
//  위 방법으로 해도 의존성 주입이 되지만 아래 방법으로 하는게 일관성있다.
  private final EntityManager em;
  
  public void save(Member member) {
    em.persist(member); // 영속성 컨텍스트에 엔티티를 넣고 나중에 commit 될 때 DB에 저장
  }
  
  public Member findOne(Long id) {
    return em.find(Member.class, id);
  }
  
  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    // JPQL과 SQL의 차이점 : JPQL은 SQL(테이블 중심)과 달리 엔티티 중심으로 쿼리를 작성한다.
  }
  
  public List<Member> findByName(String name) {
    return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
  }
}
