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

      Member member = new Member();
      member.setUsername("A");
      Member member2 = new Member();
      member2.setUsername("B");
      Member member3 = new Member();
      member3.setUsername("C");

      System.out.println("=====================");
                            //             app에서 씀
      em.persist(member); // DB SEQ = 1   |   1  // 1, 51
      em.persist(member2); // DB SEQ = 51 |   2  // MEM에서 호출
      em.persist(member3); // DB SEQ = 51 |   3  // MEM에서 호출

      System.out.println("member.id = " + member.getId());
      System.out.println("member.id = " + member2.getId());
      System.out.println("member.id = " + member3.getId());

      System.out.println("=====================");

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
        em.close();
    }

    emf.close();

  }

}
