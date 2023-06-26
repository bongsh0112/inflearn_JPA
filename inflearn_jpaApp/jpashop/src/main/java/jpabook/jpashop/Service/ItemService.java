package jpabook.jpashop.Service;

import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
  
  private final ItemRepository itemRepository;
  
  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);
  }
  
  @Transactional // 커밋이 되면 flush에서 변경을 감지해서 그걸 DB에 반영.
  public void updateItem(Long itemId, String name, int price, int stockQuantity) {
    //public void updateItem(Long itemId, UpdateItemDto itemDto)와 같이 DTO 사용해도 ok
    Item findItem = itemRepository.findOne(itemId); // => 영속성 컨텍스트에 저장된 영속상태
    findItem.setPrice(price);
    findItem.setName(name);
    findItem.setStockQuantity(stockQuantity);
  } // 이렇게 코드를 짜면 JPA가 알아서 setter들을 보고 반영해주기 때문에 따로 아무것도 호출하지 않아도 된다.
  
  public List<Item> findItems() {
    return itemRepository.findAll();
  }
  
  public Item findOne(Long itemId) {
    return itemRepository.findOne(itemId);
  }
  
}
