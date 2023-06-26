package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
  
  private final EntityManager em;
  
  public void save(Order order) {
    em.persist(order);
  }
  
  public Order findOne(Long id) {
    return em.find(Order.class, id);
  }
  
  public List<Order> findAll(OrderSearch orderSearch) {
    List<Order> findOrders = em.createQuery("select o from Order o join o.member m " +
                            "where o. status = :status " +
                            "and m.name like :name", Order.class)
            .setParameter("status", orderSearch.getOrderStatus())
            .setParameter("name", orderSearch.getMemberName())
            .setMaxResults(1000) // 최대 1000건
            .getResultList();
    // 파라미터를 쓸 필요가 없다면 setParameter들을 빼는 식으로 동적 쿼리를 써야함.
    // jpql, jpql criteria로 동적 쿼리 쓰는 것은 강의록 62쪽~ => 너무 어려운 작업임.

    return findOrders;
  }
  
  /**
   * JPA Criteria
   */
  public List<Order> findAllByCriteria(OrderSearch orderSearch) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Order> cq = cb.createQuery(Order.class);
    Root<Order> o = cq.from(Order.class);
    Join<Object, Object> m = o.join("member", JoinType.INNER); //회원과 조인
    
    List<Predicate> criteria = new ArrayList<>();
    
      //주문 상태 검색
    if (orderSearch.getOrderStatus() != null) {
      Predicate status = cb.equal(o.get("status"),
              orderSearch.getOrderStatus());
      criteria.add(status);
    }
      //회원 이름 검색
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      Predicate name =
              cb.like(m.<String>get("name"), "%" +
                      orderSearch.getMemberName() + "%");
      criteria.add(name);
    }
    cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
    TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
    return query.getResultList();
  }
}