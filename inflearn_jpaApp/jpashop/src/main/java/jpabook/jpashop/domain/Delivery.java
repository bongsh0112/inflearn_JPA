package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "delivery_id")
  private Long id;
  
  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // order만이 delivery를 관리한다. 즉, private한 참조자가 있을 때만 cascade를 쓴다.
  private Order order;
  
  @Embedded
  private Address address;
  
  @Enumerated(EnumType.STRING)
  private DeliverStatus status;
}
