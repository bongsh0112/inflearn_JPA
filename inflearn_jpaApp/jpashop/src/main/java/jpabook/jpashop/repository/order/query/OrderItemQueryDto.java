package jpabook.jpashop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderItemQueryDto {
  
  @JsonIgnore // 엔티티가 아니고 화면에 뿌리기 위한 API이기 때문에 JsonIgnore를 해줬다.
  private Long orderId;
  private String itemName;
  private int orderPrice;
  private int count;
  
  public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count) {
    this.orderId = orderId;
    this.itemName = itemName;
    this.orderPrice = orderPrice;
    this.count = count;
  }
}
