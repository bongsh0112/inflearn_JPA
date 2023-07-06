package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.*;
import jpabook.jpashop.domain.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.OrderedMapType;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 막아버리기
public class OrderItem {
  
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id")
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;
  
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;
  
  private int orderPrice;
  
  private int count;
  
//  protected OrderItem() {
//    객체를 생성할 때 생성 메소드로만 생성하게 하기 위함.. 생성자로 생성 & setter로 값 세팅 - 생성 메소드로 생성 : 둘 다 똑같은 방법이지만 유지보수가 어려움. 따라서 protected로 생성자 막아버리기
//  }
  
  //==생성 메서드==//
  public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setOrderPrice(orderPrice);
    orderItem.setCount(count);
    
    item.removeStock(count);
    return orderItem;
    
  }
  
  //==비즈니스 로직==//
  public void cancel() {
    getItem().addStock(count); // 재고 수량을 원복해준다.
  }
  
  //==조회 로직==//
  /**
   * 주문 상품 전체 가격 조회
   */
  public int getTotalPrice() {
    return getOrderPrice() * getCount(); // 주문한 상품 낱개의 가격에 주문한 개수만큼을 곱한생ㅅ
  }
}
