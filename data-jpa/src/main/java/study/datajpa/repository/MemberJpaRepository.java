package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {
  
  @PersistenceContext
  private EntityManager em;
  
  public Member save(Member member) {
    em.persist(member);
    return member;
  }
  
  public void delete(Member member) {
    em.remove(member);
  }
  
  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class).getResultList();
  }
  
  public Optional<Member> findById(Long id) {
    Member member = em.find(Member.class, id);
    return Optional.ofNullable(member);
  }
  
  public long count() {
    return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
  }
  
  public Member find(Long id) {
    return em.find(Member.class, id);
  }
  
  public List<Member> findByUserNameAndAgeGreaterThan(String username, int age) {
    return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
            .setParameter("username", username)
            .setParameter("age", age)
            .getResultList();
  }
  
  public List<Member> findByPage(int age, int offset, int limit) {
    return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
            .setParameter("age", age)
            .setFirstResult(offset) // offset : 몇번째 부터???
            .setMaxResults(limit) // limit : 언제까지???
            .getResultList();
  }
  
  public long totalCount(int age) {
    return em.createQuery("select count(m) from Member m where m.age = :age", Long.class) //total count이므로 order by는 없다
            .setParameter("age", age)
            .getSingleResult();
  }
  
  public int bulkAgePlus(int age) { // 벌크성 수정 쿼리 : 모든 직원의 연봉 10% 인상, 모든 직원의 나이 + 1 등의 벌크 연산 수행
    return em.createQuery(
                    "update Member m set m.age = m.age + 1 " +
                            "where m.age >= :age")
            .setParameter("age", age)
            .executeUpdate();
  }
  
}
