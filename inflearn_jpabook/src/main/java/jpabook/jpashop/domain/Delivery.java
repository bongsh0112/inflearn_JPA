package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity { // Order와 일대일. Order에 외래키 있는 걸로.

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // OneToOne 신경쓰기!
  private Order order;

  private String city;
  private String street;
  private String zipcode;
  private DeliveryStatus status;
}
