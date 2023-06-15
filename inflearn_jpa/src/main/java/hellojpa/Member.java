package hellojpa;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member extends BaseEntity{

  @Id @GeneratedValue
  private Long id;

  @Column(name = "USERNAME")
  private String name;

//  @Column(name = "TEAM_ID")
//  private Long teamId;

  @ManyToOne(fetch = FetchType.EAGER) // lazy fetch 타입일 때 프록시 객체를 조회함. -> 멤버 클래스만 디비에서 조
  @JoinColumn(name = "TEAM_ID")
  private Team team;

  @ManyToMany
  @JoinTable(name = "MEMBER_PRODUCT")
  private List<Product> products = new ArrayList<>();

  @OneToMany(mappedBy = "member")
  private List<MemberProduct> memberProducts = new ArrayList<>();

  // 모든 파일에 들어가야 하는 것들... 을 DBA가 정해주면 적어주어야한다;; -> BaseEntity를 만들어 SuperClass 상속하기
  /*private String createdBy;
  private LocalDateTime createdDate;
  private String lastModifiedBy;
  private LocalDateTime lastModifiedDate;*/


  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

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
}
