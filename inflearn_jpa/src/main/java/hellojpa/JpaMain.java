package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
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

      Address address = new Address("city", "street", "10000");

      Member member1 = new Member();
      member1.setName("member1");
      member1.setAddress(address);
      em.persist(member1);

      Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

      Member member2 = new Member();
      member2.setName("member2");
      member2.setAddress(copyAddress);
      em.persist(member2);

      member1.getAddress().setCity("newCity"); // 불가능!!! setter를 없앴기 때문


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
