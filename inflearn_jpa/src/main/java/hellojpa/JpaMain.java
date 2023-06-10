package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      Movie movie = new Movie();
      movie.setDirector("aaa");
      movie.setActor("bbb");
      movie.setName("cccc");
      movie.setPrice(10000);
      em.persist(movie);

      em.flush(); // 영속성 컨텍스트에 있는 것을 db에 날리고
      em.clear(); // 영속성 컨텍스트에 있는 것을 모두 삭제하니까

      Movie findMovie = em.find(Movie.class, movie.getId()); // 여기서는 db에서 조회할 수 밖에 없다!
      System.out.println("findMovie.getName() = " + findMovie.getName());
      tx.commit();

    } catch (Exception e) {
      tx.rollback();
    } finally {
        em.close();
    }

    emf.close();

  }

}
