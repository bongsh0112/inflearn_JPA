package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      Team team = new Team();
      team.setName("TeamA");
      em.persist(team);

      Member member = new Member();
      member.setName("member1");
      member.changeTeam(team); // 이 때 team.getMembers().add(member);로 team에도 연관관계 세팅을 한 것!
      em.persist(member);

//      team.getMembers().add(member);

      em.flush();
      em.clear();
       // 이런식으로 하면 DB에는 반영되지 않아 밑의 sout에서 발견되는 것이 아무것도 없음!

      Team findTeam = em.find(Team.class, team.getId());
      List<Member> members = findTeam.getMembers(); // 지연 로딩!!

      System.out.println("==============");
      for (Member m : members) {
        System.out.println("m = " + m.getName());
      }
      System.out.println("==============");

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
        em.close();
    }

    emf.close();

  }

}
