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

//      String query = "select m from Member m"; // fetch join 사용 전
      String query = "select m from Member m join fetch m.team"; // fetch join 사용시 -> 팀은 프록시가 아니라 실제 Team을 가져옴!

      List<Member> result = em.createQuery(query, Member.class)
              .getResultList();

      for(Member m : result) {
        System.out.println("m = " + m.getUsername() + " " + m.getTeam().getName());
        // Team은 LAZY에 의해 프록시로 들어오게 되고 결국 지연 로딩. 실제로 getName을 호출할 때 마다 DB에 쿼리를 날림.
        // member1, TeamA(SQL)
        // member2, TeamA(1차캐시)
        // member3, TeamB(SQL)

        // 만약 회원이 100명이라면 최악의 경우 쿼리가 100번이 나갈 것.. N + 1
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