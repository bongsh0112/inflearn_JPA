package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

  @Id @GeneratedValue
  private Long id;
  private String name;
  @OneToMany(mappedBy = "team") // 팀의 입장에서는 일대다이기 때문에 OneToMany
  private List<Member> members = new ArrayList<>();

  public void addMember(Member member) { // team->member 방향 편의 메소드
//    member.setTeam(this);
    members.add(member);
  }

  public List<Member> getMembers() {
    return members;
  }

  public void setMembers(List<Member> members) {
    this.members = members;
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
