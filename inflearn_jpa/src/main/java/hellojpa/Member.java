package hellojpa;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  private Period period;

//  Address
//  private String city;
//  private String street;
//  private String zipcode;

  @Embedded
  private Address address;

//  @Embedded
//  @AttributeOverrides({
//          @AttributeOverride(name="city",
//          column=@Column(name = "WORK_CITY")),
//          @AttributeOverride(name="street",
//          column = @Column(name = "WORK_STREET")),
//          @AttributeOverride(name="zipcode",
//          column = @Column(name = "WORK_ZIPCODE"))
//  })
//  private Address workAddress;

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

  public Period getPeriod() {
    return period;
  }

  public void setPeriod(Period period) {
    this.period = period;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
