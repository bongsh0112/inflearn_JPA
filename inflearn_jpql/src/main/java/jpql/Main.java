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
      member.setUsername("member1");
      em.persist(member);

      em.flush();
      em.clear();

      List<Member> result = em.createQuery("select m from Member m ", Member.class)
                      .getResultList(); // 엔티티 프로젝션

      Member findMember = result.get(0);
      findMember.setAge(20);

      List<Team> result2 = em.createQuery("select t from member m join m.team t", Team.class)
                      .getResultList(); // 조인 엔티티 프로젝션

      Team findTeam = result2.get(0);
      findTeam.setName("teamA");

      List<Order> result3 = em.createQuery("select o.address from Order o", Order.class)
                      .getResultList(); // 임베디드 타입 프로젝션

      Order findOrder = result3.get(0);
      findOrder.setId(1L);

      List result4 = em.createQuery("select distinct m.username, m.age from Member m")
                      .getResultList();

      Object o = result4.get(0);
      Object[] result5 = (Object[]) o;
      System.out.println("username = " + result5[0]);
      System.out.println("age = " + result5[1]);

      List<Object[]> result6 = em.createQuery("select distinct m.username, m.age from Member m")
              .getResultList();
      Object[] result7 = result6.get(0);
      System.out.println("username = " + result7[0]);
      System.out.println("age = " + result7[1]);

      List<MemberDTO> result8 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                      .getResultList();

      MemberDTO memberDTO = result8.get(0);
      System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
      System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

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