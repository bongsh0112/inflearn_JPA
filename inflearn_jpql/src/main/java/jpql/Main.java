package jpql;

import javax.persistence.*;

public class Main {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("inflearn_jpql");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Member member = new Member();
      member.setUsername("member1");
      em.persist(member);

//      TypedQuery<Member> query1 = em.createQuery("select m from Member m where m.username = :username", Member.class);
//      query1.setParameter("username", "member1");
//      Member singleResult = query1.getSingleResult();

      Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
      System.out.println("singleResult = " + result.getUsername());

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