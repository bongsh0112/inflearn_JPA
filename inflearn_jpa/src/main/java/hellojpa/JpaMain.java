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

      Member member2 = new Member();
      member2.setName("member2");
      em.persist(member2);

      Member member3 = new Member();
      member3.setName("member3");
      em.persist(member3);

      em.flush();
      em.clear();

      Member m1 = em.find(Member.class, member1.getId());
      Member m2 = em.find(Member.class, member2.getId());
      Member m3 = em.getReference(Member.class, member3.getId());

      System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass()));
//      System.out.println("m1 == m3 : " + (m1.getClass() == m3.getClass()));
      System.out.println("m1 == m3 : " + (m3 instanceof Member) );
      em.flush();
      em.clear(); // 영속성 컨텍스트 초기화. 거의 애플리케이션 처음 띄운것과 마찬가지..



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
