package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      Member member = new Member();
      member.setCreatedBy("Bong");
      member.setName("User1");
      member.setCreatedDate(LocalDateTime.now());

      em.flush(); // 영속성 컨텍스트에 있는 것을 db에 날리고
      em.clear(); // 영속성 컨텍스트에 있는 것을 모두 삭제하니까

      tx.commit();

    } catch (Exception e) {
      tx.rollback();
    } finally {
        em.close();
    }

    emf.close();

  }

}
