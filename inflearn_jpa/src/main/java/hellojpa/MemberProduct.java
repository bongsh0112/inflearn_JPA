package hellojpa;

import javax.persistence.*;
import java.util.List;

@Entity
public class MemberProduct {

  @Id @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID")
  private Product product;
}
