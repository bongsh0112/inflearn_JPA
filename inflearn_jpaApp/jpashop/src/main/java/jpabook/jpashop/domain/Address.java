package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
//@Setter // 값 타입은 immutable하게 해줘야함
public class Address {
  
  private String city;
  private String street;
  private String zipcode;
  
  protected Address() { // JPA 기본 스펙이 JPA가 생성을 할 때 reflection이나 proxy같은 것을 사용하는데 이 때 기본 생성자가 꼭 필요함.
  }
  
  public Address(String city, String street, String zipcode) {
    
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
