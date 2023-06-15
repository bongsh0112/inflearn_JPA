package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity { // Order와 일대일. Order에 외래키 있는 걸로.

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // OneToOne 신경쓰기!
  private Order order;

  @Embedded
  private Address address;
  private DeliveryStatus status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public DeliveryStatus getStatus() {
    return status;
  }

  public void setStatus(DeliveryStatus status) {
    this.status = status;
  }
}
