package jpabook.jpashop.domain.Item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
  
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long id;
  
  private String name;
  private int price;
  private int stockQuantity;

  // @양방향이지만 JsonIgnore 걸지 않은 이유는 Item과 OrderItem 간의 참조를 만들지 않았기 때문
  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();
  
  // 비즈니스 로직 - Setter를 가지고 값을 변경하는 것이 아니라 비즈니스 로직으로 변경!! //
  public void addStock(int quantity){
    this.stockQuantity += quantity;
  }
  
  public void removeStock(int quantity) throws NotEnoughStockException {
    int restStock = this.stockQuantity - quantity;
    if (restStock < 0) {
      throw new NotEnoughStockException("need more stock");
    }
    this.stockQuantity = restStock;
  }
}
