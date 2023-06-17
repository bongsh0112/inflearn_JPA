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

//      em.flush();
//      em.clear();

      // flush 자동 호출함! 왜냐면 쿼리가 나갈 때 flush는 자동 호출되기 때문에!
      int resultCount = em.createQuery("update Member m set m.age = 20")
              .executeUpdate();

      em.clear(); // 영속성 컨텍스트를 초기화하면

      Member findMember = em.find(Member.class, member1.getId()); // find는 영속성 컨텍스트를 먼저 들여다보는데 없다! 그러면 DB로 가서 찾아옴

      System.out.println("resultCount = " + resultCount);
      System.out.println("findMember.getUsername() = " + findMember.getAge()); // 그러면 벌크 연산으로 인해 바뀐 DB의 데이터가 성공적으로 찾아짐

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