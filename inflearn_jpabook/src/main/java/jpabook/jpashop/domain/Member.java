package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "MEMBER_ID")
  private Long id;

  @Column(length = 10)
  private String names;
  private String city;
  private String street;
  private String zipcode;

  @OneToMany(mappedBy = "member")
  // 특정 회원의 주문 내역을 보고싶으면 orders에 이미 외래키로 멤버 아이디가 있는 것을 이용하는 것이 좋은 설계
  // 이렇게 멤버로부터 주문 내역을 찾는 것은 별로 좋지 않은 설계임. 연관관계를 잘 끊어내는 것이 좋은 설계다
  private List<Order> orders = new ArrayList<>();

  public Member() {

  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNames() {
    return names;
  }

  public void setNames(String names) {
    this.names = names;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }
}
