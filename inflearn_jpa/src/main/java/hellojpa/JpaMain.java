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

      Child child1 = new Child();
      Child child2 = new Child();

      Parent parent = new Parent();
      parent.addChild(child1);
      parent.addChild(child2);

      em.persist(parent);

      em.flush();
      em.clear();


      Parent findParent = em.find(Parent.class, parent.getId());
//      em.remove(findParent); // 부모를 지웠으면 자식은 당연히 고아
      findParent.getChildList().remove(0);

      tx.commit();

    } catch (Exception e) {
      tx.rollback();
      e.printStackTrace();
    } finally {
        em.close();
    }

    emf.close();

  }

  private static void printMemberAndTeam(Member member) {
    String username = member.getName();
    System.out.println("username = " + username);

    Team team = member.getTeam();
    System.out.println("team.getName() = " + team.getName());
  }

}
