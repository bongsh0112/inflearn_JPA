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

      Member member = new Member();
      member.setName("hello");

      em.persist(member);

      em.flush();
      em.clear(); // 영속성 컨텍스트 초기화. 거의 애플리케이션 처음 띄운것과 마찬가지..

//      Member findMember = em.find(Member.class, member.getId());
      Member findMember = em.getReference(Member.class, member.getId());
      System.out.println("findMember.getId() = " + findMember.getId());
      System.out.println("findMember.getName() = " + findMember.getName());

      tx.commit();

    } catch (Exception e) {
      tx.rollback();
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
