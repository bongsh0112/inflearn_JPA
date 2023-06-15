package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {

      Team team = new Team();
      team.setName("Team1");
      em.persist(team);

      Member member = new Member();
      member.setName("member1");
      member.setTeam(team);
      em.persist(member);

      em.flush();
      em.clear();

//      Member findMember = em.find(Member.class, member.getId());

      List<Member> m = em.createQuery("select m from Member m", Member.class)
              .getResultList();

//      System.out.println("member.getTeam().getClass() = " + findMember.getTeam().getClass());

      tx.commit();

    } catch (Exception e) {
      tx.rollback();
      e.printStackTrace();
    } finally {
        em.close();
    }

    emf.close();

  }

  private static void printMemberAndTeam(Member member) {
    String username = member.getName();
    System.out.println("username = " + username);

    Team team = member.getTeam();
    System.out.println("team.getName() = " + team.getName());
  }

}
