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

      TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class); // 반환 타입 Member로 명확
      TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class); // 반환 타입 String으로 명확
      Query<Member> query3 = em.createQuery("select m.username, m.age from Member m");

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