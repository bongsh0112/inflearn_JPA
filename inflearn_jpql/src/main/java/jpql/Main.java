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

      List<Member> findMember = em.createNamedQuery("Member.findByUserName", Member.class)
              .setParameter("username", member1)
              .getResultList(); // NamedQuery 사용
      
      for (Member m : findMember) {
        System.out.println("m = " + m);
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