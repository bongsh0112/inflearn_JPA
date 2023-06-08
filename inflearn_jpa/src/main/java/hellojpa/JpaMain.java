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

      member.setTeamId(team.getId());
      em.persist(member);

      Member findMember = em.find(Member.class, member.getId());

      Long findTeamId = findMember.getTeamId();
      Team findTeam = em.find(Team.class, findTeamId);

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
        em.close();
    }

    emf.close();

  }

}
