package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j // log 사용하기
public class HomeController {
  
  @RequestMapping("/")
  public String home() {
    log.info("home controller"); // log를 찍고 home으로 들어감
    return "/home";
  }
}
