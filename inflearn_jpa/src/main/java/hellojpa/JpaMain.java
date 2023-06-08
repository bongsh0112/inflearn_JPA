package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {

      Team team = new Team();
      team.setName("TeamA");
      em.persist(team); // 영속상태가 되면 무조건 pk값이 세팅되고 영속상태가 됨.

      Member member = new Member();
      member.setName("member1");
      member.setTeam(team);
      em.persist(member);

      em.flush();
      em.clear();

      Member findMember = em.find(Member.class, member.getId());

      Team findTeam = findMember.getTeam();
      System.out.println("findTeam = " + findTeam.getName());

      /*Team newTeam = em.find(Team.class, 100L);
      findMember.setTeam(newTeam);*/ // 멤버에게 새로운 팀 세팅해주기

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
        em.close();
    }

    emf.close();

  }

}
