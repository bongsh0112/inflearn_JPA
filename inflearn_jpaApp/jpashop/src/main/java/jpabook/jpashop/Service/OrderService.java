package jpabook.jpashop.Service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
  
  private final OrderRepository orderRepository;
  private final MemberRepositoryOld memberRepositoryOld;
  private final ItemRepository itemRepository;
  
  /**
   * 주문
   */
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {
    
    //엔티티 조회
    Member member = memberRepositoryOld.findOne(memberId);
    Item item = itemRepository.findOne(itemId);
    
    //배송정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());
    
    //주문상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count); // 생성 메소드로 객체 생성
    
    //주문 생성
    Order order = Order.createOrder(member, delivery, orderItem); // 생성 메소드로 객체 생성
    
    //주문 저장
    orderRepository.save(order); // 위의 delivery, orderItem, order는 왜 persist를 하지 않나? 바로 order와 delivery, orderitem이 cascade 옵션을 가지고 매핑되었기 때문.
    
    return order.getId();
  }
  
  //취소
  /**
   * 주문 취소
   */
  @Transactional
  public void cancelOrder(Long orderId) {
    //주문 엔티티 조회
    Order order = orderRepository.findOne(orderId);
    //주문 취소
    order.cancel();
  }
  
  //검색
  public List<Order> findOrders(OrderSearch orderSearch) {
    return orderRepository.findAll(orderSearch);
  }
}
