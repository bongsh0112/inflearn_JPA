package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member; // orders에 있는 member의 fk
  
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // order만이 orderitems를 관리한다. private한 관계에서만 cascade를 쓰기!
  private List<OrderItem> orderItems = new ArrayList<>();
  
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;
  
  private LocalDateTime orderDate;
  
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
  
  // 연관관계 편의 메서드 : 양쪽 세팅을 원자적으로 한번에 해결 //
  public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
  }
  
  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }
  
  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }
  
  //==생성 메서드==// => 생성 시점에 뭘 바꾸고 싶으면 이거만 바꾸면 됨!
  public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);
    for (OrderItem orderItem : orderItems) {
      order.addOrderItem(orderItem);
    }
    order.setStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }
  
  //==비즈니스 로직==//
  /**
   * 주문 취소
   */
  public void cancel() {
    if (delivery.getStatus() == DeliverStatus.COMP) {
      throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
    }
    
    this.setStatus(OrderStatus.CANCEL);
    for (OrderItem orderItem : orderItems) {
      orderItem.cancel();
    }
  }
  
  //==조회 로직==//
  /**
   * 전체 주문 가격 조회
   */
  public int getToTalPrice() {
//    int totalPrice = 0;
//    for (OrderItem orderItem : orderItems) { // 주문한 모든 상품의
//      totalPrice += orderItem.getTotalPrice(); // 주문한 수량만큼의 가격을 가져다 더한다
//    }
//    return totalPrice;
    int totalPrice = orderItems.stream()
            .mapToInt(OrderItem::getTotalPrice)
            .sum();
    return totalPrice;
  }
}


