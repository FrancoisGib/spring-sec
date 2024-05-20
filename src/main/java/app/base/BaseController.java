package app.base;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class BaseController {
  private final BaseService baseService;

  private static final int NUMBER = (int) (Math.random() * 100);
  @GetMapping("/test")
  public String test() {
    return String.valueOf(NUMBER);
  }

  @GetMapping("/count")
  public ResponseEntity<Integer> getCount() {
    return ResponseEntity.ok(baseService.getCount());
  }
}
