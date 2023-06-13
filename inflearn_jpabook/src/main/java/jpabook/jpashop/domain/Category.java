package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends BaseEntity { // Item과 다대다

  @Id @GeneratedValue
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "PARENT_ID")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> child = new ArrayList<>();
  // 양방향으로 만들어서 child까지 만들 수 있음. 위에꺼랑 셀프로 잡혔다.
  // 카테고리가 쭉 내려가서 셀프로 잡힘.

  @ManyToMany
  @JoinTable(name = "CATEGORY_ITEM",
        joinColumns = @JoinColumn(name = "CATEGORY_ID"),
          inverseJoinColumns = @JoinColumn(name = "ITEM_ID") // 관계 테이블이 있다고 가정하고 양방향 매핑.
  )
  private List<Item> items = new ArrayList<>();

}
