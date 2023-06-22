package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
  
  private final EntityManager em;
  
  public void save(Item item) {
    if (item.getId() == null) {
      em.persist(item); // 신규 등록. item은 jpa에 저장하기 전까지 id값이 없다 (완전히 새로운 객체)
    } else {
      em.merge(item); // DB에 원래 있던 데이터라면 update처럼 DB의 데이터와 합병. 자세한건 나중에!
    }
  }
  
  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }
  
  public List<Item> findAll() {
    return em.createQuery("select i from Item i", Item.class)
            .getResultList();
  }
  
}
