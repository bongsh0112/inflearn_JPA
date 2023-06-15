package jpql;

import javax.persistence.*;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("inflearn_jpql");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Team team = new Team();
      team.setName("teamA");
      em.persist(team);

      Member member = new Member();
      member.setUsername("member1");
      member.setAge(10);
      member.setType(MemberType.ADMIN);
      em.persist(member);

      member.setTeam(team);

      em.flush();
      em.clear();

//      String query =
//              "select coalesce(m.username, '이름 없는 회원') from Member m"; // coalesce
      String query = "select nullif(m.username, '관리자') as username from Member m"; // nullif

    List<String> result = em.createQuery(query, String.class)
                    .getResultList();

    for(String s : result) {
      System.out.println("s = " + s);
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