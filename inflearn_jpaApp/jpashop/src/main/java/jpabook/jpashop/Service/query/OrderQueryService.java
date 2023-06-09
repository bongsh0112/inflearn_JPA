package jpabook.jpashop.Service.query;

import jpabook.jpashop.api.OrderApiController;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {
  
  private final OrderRepository orderRepository;
  
  public List<OrderDto> ordersV3() {
    List<Order> orders = orderRepository.findAllWithItem();
    
    List<OrderDto> result = orders.stream()
            .map(OrderDto::new)
            .collect(toList());
    
    return result;
  }
}
