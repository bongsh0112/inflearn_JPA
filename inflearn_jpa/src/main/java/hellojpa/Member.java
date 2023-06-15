package hellojpa;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Member extends BaseEntity {

  @Id @GeneratedValue
  @Column(name = "MEMBER_ID")
  private Long id;

  @Column(name = "USERNAME")
  private String name;
//  Period
//  private LocalDateTime startDate;
//  private LocalDateTime endDate;

  @Embedded
  private Address homeAddress;

  @ElementCollection
  @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
  @JoinColumn(name = "MEMBER_ID")) // @JoinColumn으로 외래키 잡기
  @Column(name = "FOOD_NAME") // 임베디드 타입이 아니고 정의한게 아니라서...예외적
  private Set<String> favoriteFoods = new HashSet<>();

//  @ElementCollection
//  @CollectionTable(name = "ADDRESS_HISTORY", joinColumns =
//  @JoinColumn(name = "MEMBER_ID"))
//  private List<Address> addressHistory = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "MEMBER_ID")
  private List<AddressEntity> addressHistory = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
  }

  public Set<String> getFavoriteFoods() {
    return favoriteFoods;
  }

  public void setFavoriteFoods(Set<String> favoriteFoods) {
    this.favoriteFoods = favoriteFoods;
  }

  public List<Address> getAddressHistory() {
    return addressHistory;
  }

  public void setAddressHistory(List<Address> addressHistory) {
    this.addressHistory = addressHistory;
  }

  //  public Period getPeriod() {
//    return period;
//  }
//
//  public void setPeriod(Period period) {
//    this.period = period;
//  }
//
//  public Address getAddress() {
//    return address;
//  }
//
//  public void setAddress(Address address) {
//    this.address = address;
//  }
}
