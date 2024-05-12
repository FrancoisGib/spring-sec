package app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BaseController {
  private static final int NUMBER = (int) (Math.random() * 100);
  @GetMapping("/test")
  public String test() {
    return String.valueOf(NUMBER);
  }
}
