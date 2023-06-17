package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("inflearn_jpql");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Team teamA = new Team();
      teamA.setName("teamA");
      em.persist(teamA);

      Team teamB = new Team();
      teamB.setName("teamB");
      em.persist(teamB);

      Member member1 = new Member();
      member1.setUsername("member1");
      member1.setTeam(teamA);
      em.persist(member1);

      Member member2 = new Member();
      member2.setUsername("member2");
      member2.setTeam(teamA);
      em.persist(member2);

      Member member3 = new Member();
      member3.setUsername("member3");
      member3.setTeam(teamB);
      em.persist(member3);

      em.flush();
      em.clear();

      String query = "select m from Member m where m = :member"; // 엔티티 자체를 사용하기 - 엔티티 자체를 사용하여 기본 키 뽑아오게 하기

      Member findMember = em.createQuery(query, Member.class)
              .setParameter("member", member1)
              .getSingleResult();

      String query2 = "select m from Member m where m.id = :memberId"; // 기본 키를 사용하기 - 엔티티의 식별자인 기본 키 직접 사용

      Member findMember2 = em.createQuery(query2, Member.class)
              .setParameter("memberId", member1.getId())
              .getSingleResult();

      String query3 = "select m from Member m where m.team = :team"; // 외래 키를 사용하기 - 연관관계에 있는 엔티티의 외래 키 사용하여 조인 비슷하게 쓰기

      List<Member> findMember3 = em.createQuery(query3, Member.class)
              .setParameter("team", teamA)
              .getResultList();

      System.out.println("findMember = " + findMember);
      System.out.println("findMember2 = " + findMember2);
      for (Member m : findMember3) {
        System.out.println("findMember3 = " + m.getUsername());
      }
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      e.printStackTrace();
    } finally {
      em.close();
    }

    emf.close();
  }
}