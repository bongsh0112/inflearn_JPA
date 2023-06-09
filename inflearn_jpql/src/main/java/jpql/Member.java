package jpql;

import javax.persistence.*;

@Entity
@NamedQuery( // NamedQuery
        name = "Member.findByUserName",
        query = "select m from Member m where m.username = :username"
)
public class Member {

  @Id @GeneratedValue
  private Long id;
  private String username;
  private int age;
  @Enumerated(EnumType.STRING)
  private MemberType type;

  public void changeTeam(Team team) {
    this.team = team;
    team.getMembers().add(this);
  }

  @ManyToOne(fetch = FetchType.LAZY) // 다대일 일때 지연 로딩 !!!
  @JoinColumn(name = "team_id")
  private Team team;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public void setType(MemberType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Member{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", age=" + age +
            '}';
  }
}
