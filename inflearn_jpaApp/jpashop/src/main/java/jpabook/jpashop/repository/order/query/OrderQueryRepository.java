package jpabook.jpashop.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

  private final EntityManager em;
  
  public List<OrderQueryDto> findOrderQueryDtos()  {
    List<OrderQueryDto> result = findOrders(); // query 1번 -> N개
    
    result.forEach(o -> {
      List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId()); // 컬렉션 부분을 직접 채운다 // Query 2번 -> N번 => N + 1문제
      o.setOrderItems(orderItems);
    });
    
    return result;
  }
  
  private List<OrderItemQueryDto> findOrderItems(Long orderId) {
    return em.createQuery(
            "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                    " from OrderItem oi" +
                    " join oi.item i" +
                    " where oi.order.id = :orderId", OrderItemQueryDto.class)
            .setParameter("orderId", orderId)
            .getResultList();
  }
  
  private List<OrderQueryDto> findOrders() {
    return em.createQuery(
                    "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, d.address, o.status)" +
                            " from Order o" +
                            " join o.member m" +
                            " join o.delivery d", OrderQueryDto.class)
            .getResultList();
  }
  
  public List<OrderQueryDto> findAllByDto_optimization() {
    List<OrderQueryDto> result = findOrders(); // root entity 조회
    
    List<OrderItemQueryDto> orderItems = findOrderItemMap(result); // 주문 데이터만큼 한방에 Memory에 올려줌
    
    Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
            .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId())); // 루프를 돌면서 모자랐던 orderItem에 데이터 채워줌.
    
    result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
    
    return result;
  }
  
  public List<OrderItemQueryDto> findOrderItemMap(List<OrderQueryDto> result) {
    List<Long> orderIds = result.stream()
                    .map(o -> o.getOrderId())
                    .collect(Collectors.toList());
    
    List<OrderItemQueryDto> orderItems = em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                    " from OrderItem oi" +
                    " join oi.item i" +
                    " where oi.order.id in :orderIds", OrderItemQueryDto.class)
            .setParameter("orderIds", orderIds)
            .getResultList();
    return orderItems;
  }
  
  public List<OrderFlatDto> findAllByDto_flat() {
    return em.createQuery(
                    "select new " +
                            "jpabook.jpashop.api.OrderFlatDto(jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
                            "from Order d " +
                            "join o.member m " +
                            "join o.delivery d " +
                            "join o.orderItems oi " +
                            "join oi.item i", OrderFlatDto.class)
            .getResultList();
  }
}
