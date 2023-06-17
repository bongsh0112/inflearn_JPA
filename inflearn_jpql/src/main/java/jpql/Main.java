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
      String query = "select t from Team t join fetch t.members"; // fetch join 사용시 -> 팀은 프록시가 아니라 실제 Team을 가져옴!

      List<Team> result = em.createQuery(query, Team.class)
              .getResultList();

      for(Team t : result) {
        System.out.println("team = " + t.getName() + " members = " + t.getMembers().size());
        for (Member member : t.getMembers()) {
          System.out.println(" -> member = " + member);
        }
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