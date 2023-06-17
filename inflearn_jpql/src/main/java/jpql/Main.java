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

      Team team = new Team();
      em.persist(team);

      Member member = new Member();
      member.setUsername("관리자1");
      member.setTeam(team);
      em.persist(member);

      Member member2 = new Member();
      member2.setUsername("관리자2");
      member.setTeam(team);
      em.persist(member2);

      em.flush();
      em.clear();

      String query = "select m.username from Member m"; // 경로 표현식 사용 -> 상태 필드 사용
//      String query = "select m.team From Member m"; // 경로 표현식 사용 시 주의 -> 단일 값 연관 경로 조회 시 묵시적 내부 조인 발생
      /* String query = "select t.members.username From Team t"; // 경로 표현식 사용 시 주의 -> 컬렉션 값 연관 경로 조회 시 묵시적 내부 조인 발생. 이 코드는 에러발생
      Collection result = em.createQuery(query, Collection.class).getResultList(); */
//      String query = "select t.members from Team t join t.members m"; // 명시적 조인을 통한 조회

      List<Team> result = em.createQuery(query, Team.class)
                    .getResultList();

      for(Team m : result) {
        System.out.println("s = " + m);
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