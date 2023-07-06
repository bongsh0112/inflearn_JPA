package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Order
 * Order -> Member
 * Order ->
 * => xToOne 관계인 것들만 다룬다! Collection과 매핑된, 즉 n대다 관계는 이번 섹션에서 다루지 않을 것.
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
  
  private final OrderRepository orderRepository;
  private final OrderSimpleQueryRepository orderSimpleQueryRepository;
  
  @GetMapping("/api/v1/simple-orders")
  public List<Order> ordersV1() { // Order에 Member도 있고 Delivery도 있으니까 그냥 Order 리스트로 가져온다!
    List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
    return all;
  } // 무한루프에 빠진다! Order -> Member -> Order -> Member -> .... 따라서 이 방법을 쓰려면 @JsonIgnore를 양방향 매핑인 곳에 모두 설정해줘야한다.
  
  @GetMapping("api/v2/simple-orders")
  public List<SimpleOrderDto> ordersV2() {
    List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
    List<SimpleOrderDto> result = orders.stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());
    
    return result;
  }
  
  @GetMapping("api/v3/simple-orders")
  public List<SimpleOrderDto> ordersV3() {
    List<Order> orders = orderRepository.findAllWithMemberDelivery(1, 100); // 엔티티를 조회하고
    return orders.stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList()); // 엔티티를 DTO로 변환함.
  }
  
  @GetMapping("api/v4/simple-orders")
  public List<OrderSimpleQueryDto> ordersV4() {
    return orderSimpleQueryRepository.findOrderDtos();
  }
  @Data
  static class SimpleOrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    
    public SimpleOrderDto(Order order) {
      orderId = order.getId();
      name = order.getMember().getName(); // lazy 초기화
      orderDate = order.getOrderDate();
      orderStatus = order.getStatus();
      address = order.getMember().getAddress(); // lazy 초기화
    }
  }
}
