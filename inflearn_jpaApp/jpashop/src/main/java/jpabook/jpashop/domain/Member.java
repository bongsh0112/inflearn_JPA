package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;
  
  private String name;
  
  @Embedded
  private Address address;
  
  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>(); // 여기에 뭔가 값을 넣는다고 해도 fk값이 바뀌지 않는다.
}
