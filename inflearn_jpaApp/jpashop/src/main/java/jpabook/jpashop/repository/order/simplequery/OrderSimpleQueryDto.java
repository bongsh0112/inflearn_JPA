package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto { // DTO는 엔티티를 직접 참조해도 상관없음
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;
  
  public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
  }
}