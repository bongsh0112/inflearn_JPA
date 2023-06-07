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

//      Member findMember = em.find(Member.class, 1L);
//      findMember.setName("HelloJPA"); // 값만 바꿨는데 어떻게...? jpa를 통해 갖고오면 jpa가 관리함. update 할 때 바뀐게 있는지 jpa가 관리하고 있다면 바꿔준다.

      List<Member> result = em.createQuery("select m from Member as m", Member.class)
//              .setFirstResult(5)
//              .setMaxResults(8) // 페이지네이션 짜기? -> 5번부터 8개 가져와
              .getResultList();

      for (Member m : result) {
        System.out.println("member.name = " + m.getName());
      }

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
        em.close();
    }

    emf.close();

  }
}
