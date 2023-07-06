package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;

import java.time.LocalDateTime;

public class OrderFlatDto { // 데이터 자체를 flat하게 만들어줌. 즉 JSON 안에서 다시 {}를 갖지 않도록 하기.
  
  private Long orderId;
  private String name;
  private LocalDateTime orderDate; //주문시간 private OrderStatus orderStatus;
  private OrderStatus orderStatus;
  private Address address;
  
  private String itemName; // 상품 명
  private int orderPrice; // 주문 가격
  private int count; // 주문 수량
  
  public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, String itemName, int orderPrice, int count) {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
    this.itemName = itemName;
    this.orderPrice = orderPrice;
    this.count = count;
  }
}
