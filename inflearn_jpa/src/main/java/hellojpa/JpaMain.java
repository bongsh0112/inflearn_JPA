package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

      Member member1 = new Member();
      member1.setName("member1");
      em.persist(member1);

      em.flush();
      em.clear();

      Member refMember = em.getReference(Member.class, member1.getId());
      System.out.println("refMember.getClass() = " + refMember.getClass());

      em.detach(refMember);
      //em.close();
      //em.clear();

      System.out.println(refMember.getName());

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
