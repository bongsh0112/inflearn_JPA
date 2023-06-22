package jpabook.jpashop.Service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class ItemServiceTest {
  
  @Autowired
  ItemService itemService;
  @Autowired
  ItemRepository itemRepository;
  @Autowired
  EntityManager em;
  
  @Test
  public void 상품등록() throws Exception {
    //given
    Book book = new Book();
    book.setName("JAVA to Kotlin");
    book.setAuthor("Duncun McGregor");
    book.setIsbn("102938");
    
    //when
    itemService.saveItem(book);
    
    //then
    Assertions.assertThat(book).isEqualTo(book);
  }
  
  @Test
  public void 재고_증가() throws Exception {
    //given
    Book book = new Book();
    book.setAuthor("kim");
    book.setName("ORM JPA");
    book.setIsbn("1234");
    
    //when
    itemService.saveItem(book);
    book.addStock(5);
    
    //then
    assertEquals(book, itemRepository.findOne(book.getId()));
  }
  
  @Test(expected = NotEnoughStockException.class)
  public void 재고_감소() throws Exception {
      //given
    List<Item> book = itemRepository.findAll();

      //when
    for (Item i : book) {
      i.removeStock(5);
    }

      //then
    System.out.println("예외가 발생해야 한다.");
  }

}
