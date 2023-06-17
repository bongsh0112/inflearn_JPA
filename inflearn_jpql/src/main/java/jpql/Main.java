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

      Member member = new Member();
      member.setUsername("관리자1");
      member.setType(MemberType.ADMIN);
      em.persist(member);

      Member member2 = new Member();
      member2.setUsername("관리자2");
      member2.setType(MemberType.ADMIN);
      em.persist(member2);

      em.flush();
      em.clear();

      String query = "select concat('a', 'b') From Member m";
//      String query = "select substring(m.username, 2, 3) From Member m";

//      String query = "select size(t.members) from Team t"; // 컬렉션의 크기를 보여줌.
//      String query = "select index(t.members) from Team t"; // 값 타입 컬렉션에서 컬렉션의 위치 값. 안쓰는게 나음

//      String query = "select locate('de', 'abcdefg') From Member m";
//      List<Integer> result = em.createQuery(query, Integer.class)
//                    .getResultList();
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