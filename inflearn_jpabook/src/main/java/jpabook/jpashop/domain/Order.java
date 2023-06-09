package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ORDER_ID")
  private Long id;

//  @Column(name = "MEMBER_ID")
//  private Long memberId;

  @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL) // 나를 주문한 회원은 하나!
  @JoinColumn(name="MEMBER_ID")
  private Member member;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems= new ArrayList<>();

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "DELIVERY_ID")
  private Delivery delivery;

  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  public Order() {

  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this); // orderItem에도 니 주문이 나라는 것을 각인시키기
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }
}
