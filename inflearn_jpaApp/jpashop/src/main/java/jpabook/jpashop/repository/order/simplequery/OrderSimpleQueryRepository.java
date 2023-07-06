package jpabook.jpashop.repository.order.simplequery;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
  
  private final EntityManager em;
  
  public List<OrderSimpleQueryDto> findOrderDtos() {
    return em.createQuery(
            "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" + // API 스펙에 너무 fit함.
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d", OrderSimpleQueryDto.class
    ).getResultList();
  }
  // API 스펙에 매우 fit 한 경우(화면에 매우 fit한 경우)에는 이렇게 빼주는 것이 맞다.
}
