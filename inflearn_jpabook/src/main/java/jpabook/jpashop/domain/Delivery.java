package jpabook.jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Delivery { // Order와 일대일. Order에 외래키 있는 걸로.

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(mappedBy = "delivery")
  private Order order;

  private String city;
  private String street;
  private String zipcode;
  private DeliveryStatus status;
}
