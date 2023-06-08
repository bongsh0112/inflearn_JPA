package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Table(name = "MEMBER")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;
  @Column(name = "name", nullable = false)
  private String username;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }



  public Member() {

  }
}
